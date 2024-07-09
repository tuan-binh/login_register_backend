package com.ra.service.impl;

import com.ra.constants.RoleName;
import com.ra.dto.request.LoginRequest;
import com.ra.dto.request.RegisterRequest;
import com.ra.dto.response.JwtResponse;
import com.ra.exception.CustomException;
import com.ra.model.Roles;
import com.ra.model.Users;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.principal.UserPrincipal;
import com.ra.service.IRoleService;
import com.ra.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	private final JwtProvider jwtProvider;
	private final AuthenticationManager manager;
	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final IRoleService roleService;
	
	@Override
	public JwtResponse login(LoginRequest loginRequest) throws CustomException {
		Authentication authentication;
		try {
			authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (AuthenticationException e) {
			throw new CustomException("Mày sai tài khoản mật khẩu rồi", HttpStatus.BAD_REQUEST);
		}
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		if (!userPrincipal.getStatus()) {
			throw new CustomException("User is blocked", HttpStatus.BAD_REQUEST);
		}
		return JwtResponse.builder()
				  .accessToken(jwtProvider.generateAccessToken(userPrincipal))
				  .fullName(userPrincipal.getFullName())
				  .email(userPrincipal.getEmail())
				  .status(userPrincipal.getStatus())
				  .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
				  .build();
	}
	
	@Override
	public void register(RegisterRequest registerRequest) throws CustomException {
		Set<Roles> roles = new HashSet<>();
		roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
		Users users = Users.builder()
				  .fullName(registerRequest.getFullName())
				  .email(registerRequest.getEmail())
				  .password(passwordEncoder.encode(registerRequest.getPassword()))
				  .roles(roles)
				  .status(true)
				  .build();
		userRepository.save(users);
	}
}
