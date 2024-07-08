package com.ra.service;

import com.ra.dto.request.LoginRequest;
import com.ra.dto.request.RegisterRequest;
import com.ra.dto.response.JwtResponse;
import com.ra.exception.CustomException;

public interface IUserService {
	JwtResponse login(LoginRequest loginRequest) throws CustomException;
	void register(RegisterRequest registerRequest) throws CustomException;
}
