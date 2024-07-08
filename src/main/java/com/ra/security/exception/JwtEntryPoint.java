package com.ra.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.constants.EHttpStatus;
import com.ra.dto.ResponseWrapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		log.error("Un Authentication {}", authException.getMessage());
		response.setHeader("error", "UNAUTHORIZED");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(401);
		Map<String, Object> errors = new HashMap<>();
		errors.put(
				  "error",
				  ResponseWrapper.builder()
							 .status(EHttpStatus.FAILED)
							 .code(401)
							 .data(authException.getMessage())
							 .build()
		);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getOutputStream(), errors);
	}
}
