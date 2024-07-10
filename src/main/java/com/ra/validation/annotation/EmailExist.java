package com.ra.validation.annotation;

import com.ra.validation.handle.EmailExistHandle;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailExistHandle.class)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailExist {
	String message() default "email is already exist";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
