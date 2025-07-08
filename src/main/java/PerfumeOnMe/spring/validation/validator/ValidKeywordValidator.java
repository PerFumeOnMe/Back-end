package PerfumeOnMe.spring.validation.validator;

import PerfumeOnMe.spring.validation.annotation.ValidKeyword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidKeywordValidator implements ConstraintValidator<ValidKeyword, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// null 또는 공백이거나 2자 미만일 경우 false
		return value != null && value.trim().length() >= 2;
	}
}