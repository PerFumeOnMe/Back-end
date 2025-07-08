package PerfumeOnMe.spring.validation.validator;

import PerfumeOnMe.spring.validation.annotation.ValidPage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PageValidator implements ConstraintValidator<ValidPage, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value != null && value >= 0;
	}
}