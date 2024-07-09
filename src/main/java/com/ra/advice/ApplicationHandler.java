package com.ra.advice;

import com.ra.constants.EHttpStatus;
import com.ra.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationHandler {
	
	/**
	 * @param ex MethodArgumentNotValidException
	 * @apiNote handle valid request
	 * */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}
	
	/**
	 * @param ex CustomException
	 * @apiNote handle custom exception
	 * */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error",ex.getMessage());
		return new ResponseEntity<>(errors, ex.getStatus());
	}
	
}
