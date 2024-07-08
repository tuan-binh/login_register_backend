package com.ra.controller;

import com.ra.constants.EHttpStatus;
import com.ra.dto.ResponseWrapper;
import com.ra.dto.request.LoginRequest;
import com.ra.dto.request.RegisterRequest;
import com.ra.exception.CustomException;
import com.ra.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final IUserService userService;
	
	/**
	 * @param loginRequest LoginRequest
	 * @apiNote handle login with { email, password }
	 * */
	@PostMapping("/login")
	public ResponseEntity<?> handleLogin(@Valid @RequestBody LoginRequest loginRequest) throws CustomException {
		return ResponseEntity.ok().body(ResponseWrapper.builder()
				  .status(EHttpStatus.SUCCESS)
				  .code(200)
				  .data(userService.login(loginRequest))
				  .build());
	}
	
	/**
	 * @param registerRequest RegisterRequest
	 * @apiNote handle register with { fullName, email, password }
	 * */
	@PostMapping("/register")
	public ResponseEntity<?> handleRegister(@Valid @RequestBody RegisterRequest registerRequest) throws CustomException {
		userService.register(registerRequest);
		return ResponseEntity.created(URI.create("api/v1/auth/register")).body(ResponseWrapper.builder()
				  .status(EHttpStatus.SUCCESS)
				  .code(201)
				  .data("Register successfully")
				  .build());
	}
	
}
