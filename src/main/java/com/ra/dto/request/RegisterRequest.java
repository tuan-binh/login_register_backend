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
	@NotBlank(message = "fullName must be not blank")
	private String fullName;
	@NotBlank(message = "email must be not blank")
	private String email;
	@NotBlank(message = "password must be not blank")
	private String password;
}
