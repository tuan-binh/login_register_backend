package com.ra.validation.handle;

import com.ra.repository.IUserRepository;
import com.ra.validation.annotation.EmailExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailExistHandle implements ConstraintValidator<EmailExist,String> {
	private final IUserRepository userRepository;
	
	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		return !userRepository.existsByEmail(s);
	}
}
