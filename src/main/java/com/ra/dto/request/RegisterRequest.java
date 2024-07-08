package com.ra.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterRequest {
	@NotEmpty(message = "fullName must be not empty")
	@NotBlank(message = "fullName must be not blank")
	private String fullName;
	@NotEmpty(message = "email must be not empty")
	@NotBlank(message = "email must be not blank")
	private String email;
	@NotEmpty(message = "password must be not empty")
	@NotBlank(message = "password must be not blank")
	private String password;
}
