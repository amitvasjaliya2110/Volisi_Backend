package com.volisi.service.impl;

import com.volisi.constant.Constant;
import com.volisi.dto.response.LoginResponse;
import com.volisi.entity.User;
import com.volisi.enums.ResultCode;
import com.volisi.exception.VolisiAppException;
import com.volisi.repository.UserRepository;
import com.volisi.service.AuthenticationService;
import com.volisi.service.BlacklistTokenService;
import com.volisi.service.JwtService;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  private static final long EXPIRATION_MINUTES = 5;
  private final UserDetailsService userDetailsService;
  private final AuthenticationManager authenticationManager;
  private final MessageSource messageSource;
  private final JwtService jwtService;
  private final BlacklistTokenService blacklistTokenService;
  private final UserRepository userRepository;

  public AuthenticationServiceImpl(
      UserDetailsService userDetailsService,
      AuthenticationManager authenticationManager,
      MessageSource messageSource,
      JwtService jwtService,
      BlacklistTokenService blacklistTokenService,
      UserRepository userRepository) {
    this.userDetailsService = userDetailsService;
    this.authenticationManager = authenticationManager;
    this.messageSource = messageSource;
    this.jwtService = jwtService;
    this.blacklistTokenService = blacklistTokenService;
    this.userRepository = userRepository;
  }

  @Override
  public LoginResponse authenticate(User user) {
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
    try {
      authenticationManager.authenticate(authentication);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException(
          messageSource.getMessage(
              "invalid.credential.error",
              new String[] {Constant.AUTHENTICATION},
              Locale.getDefault()));
    }
    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
    String token = jwtService.generateToken(userDetails);
    Long userId = userRepository.findByUsername(user.getUsername()).getId();
    Date tokenExpiryTime = jwtService.extractExpiration(token);
    return new LoginResponse(userId, user.getUsername(), token, tokenExpiryTime);
  }

  @Override
  public String refreshToken(String token) {
    try {
      if (token != null && !jwtService.isTokenExpired(token) && token.startsWith(Constant.BEARER)) {
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.generateToken(userDetails);
      }
    } catch (Exception e) {
      throw new VolisiAppException(
          ResultCode.REFRESH_TOKEN_EXCEPTION.getCode(),
          messageSource.getMessage(
              "refresh.token.data.fail",
              new String[] {Constant.AUTHENTICATION},
              Locale.getDefault()));
    }
    return "Token expired or invalid";
  }

  @Override
  public void logout(String token) {
    blacklistTokenService.blacklistToken(token);
  }

  @Override
  public User generateOtp(Long id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new VolisiAppException(
                        ResultCode.USER_NOT_FOUND.getCode(),
                        messageSource.getMessage(
                            "user.not.found", new Object[] {}, Locale.getDefault())));

    user.setOtp(String.valueOf(new Random().nextInt(900000) + 100000));
    user.setExpirationTime(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));

    return userRepository.save(user);
  }

  @Override
  public void verifyOtp(User user) {
    User userData =
        userRepository
            .findById(user.getId())
            .orElseThrow(
                () ->
                    new VolisiAppException(
                        ResultCode.USER_NOT_FOUND.getCode(),
                        messageSource.getMessage(
                            "user.not.found",
                            new String[] {Constant.USER},
                            LocaleContextHolder.getLocale())));

    Optional<String> optionalOtpValue = Optional.ofNullable(user.getOtp());
    Optional<LocalDateTime> expirationTime = Optional.ofNullable(userData.getExpirationTime());

    if (optionalOtpValue.isPresent() && expirationTime.isPresent()) {
      if ((optionalOtpValue.get()).equals(userData.getOtp())
          && (expirationTime.get()).isAfter(LocalDateTime.now())) {
        userRepository.updateVerified(true, userData.getId());
      } else {
        throw new VolisiAppException(
            ResultCode.OTP_VERIFIED_FAILED.getCode(),
            messageSource.getMessage("otp.verified.failed", new Object[] {}, Locale.getDefault()));
      }
    }
  }
}
