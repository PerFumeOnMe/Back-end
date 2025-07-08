package PerfumeOnMe.spring.validation.validator;

import PerfumeOnMe.spring.validation.annotation.ValidSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SizeValidator implements ConstraintValidator<ValidSize, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value != null && value >= 1;
	}
}