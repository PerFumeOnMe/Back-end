package PerfumeOnMe.spring.user.validation.validator;

import PerfumeOnMe.spring.common.enums.UserGender;
import PerfumeOnMe.spring.user.validation.annotation.ExistUserGender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistUserGenderValidator implements ConstraintValidator<ExistUserGender, String> {

	@Override
	public void initialize(ExistUserGender constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty())
			return false;
		try {
			UserGender.valueOf(value.toUpperCase());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
