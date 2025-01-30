package com.volisi.dto.request;

import com.volisi.model.QuizDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlayerQuizUpdateRequest {
  @NotNull
  @Min(value = 1, message = "{min.id.length}")
  long id;

  @NotNull QuizDto quiz;

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{name.pattern.error}")
  @Size(max = 30)
  String name;

  @Min(value = 0)
  Integer players;

  @DecimalMin(value = "0.01")
  Double time;
}
