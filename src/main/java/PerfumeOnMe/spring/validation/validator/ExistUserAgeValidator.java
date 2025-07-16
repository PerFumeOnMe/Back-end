package PerfumeOnMe.spring.validation.validator;

import PerfumeOnMe.spring.domain.enums.Age;
import PerfumeOnMe.spring.validation.annotation.ExistUserAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistUserAgeValidator implements ConstraintValidator<ExistUserAge, String> {

	@Override
	public void initialize(ExistUserAge constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null || value.isEmpty())
			return false;
		try {
			Age.valueOf(value.toUpperCase());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
