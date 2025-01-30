package com.volisi.security;

import com.volisi.constant.Constant;
import com.volisi.service.BlacklistTokenService;
import com.volisi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final BlacklistTokenService blacklistTokenService;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  public JwtAuthenticationFilter(
      JwtService jwtService,
      @Lazy UserDetailsService userDetailsService,
      BlacklistTokenService blacklistTokenService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.blacklistTokenService = blacklistTokenService;
  }

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws IllegalStateException {
    try {
      String authHeader = request.getHeader(Constant.AUTHORIZATION);
      if (authHeader != null && authHeader.startsWith(Constant.BEARER)) {
        String token = authHeader.substring(7);
        UserDetails userDetails = getUserDetailsFromToken(token);
        if ((userDetails != null)
            && jwtService.validateToken(token, userDetails)
            && !blacklistTokenService.isTokenBlacklisted(token)) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(request, response);
    } catch (Exception ex) {
      resolver.resolveException(request, response, null, ex);
    }
  }

  private UserDetails getUserDetailsFromToken(String token) {
    String username = jwtService.extractUsername(token);
    return (username != null) ? userDetailsService.loadUserByUsername(username) : null;
  }
}
