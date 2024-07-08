package com.ra.service.impl;

import com.ra.constants.RoleName;
import com.ra.exception.CustomException;
import com.ra.model.Roles;
import com.ra.repository.IRoleRepository;
import com.ra.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
	private final IRoleRepository roleRepository;
	
	@Override
	public Roles findByRoleName(RoleName roleName) throws CustomException {
		return roleRepository.findByRoleName(roleName).orElseThrow(() -> new CustomException("role not found", HttpStatus.NOT_FOUND));
	}
}
