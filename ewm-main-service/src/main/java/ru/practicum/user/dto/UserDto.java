package ru.practicum.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class UserDto {

  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;
}
