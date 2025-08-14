package PerfumeOnMe.spring.user.validation.validator;

import java.util.List;

import PerfumeOnMe.spring.user.validation.annotation.ValidUserNote;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUserNoteValidator implements ConstraintValidator<ValidUserNote, List<Long>> {

	@Override
	public void initialize(ValidUserNote constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(List<Long> longs, ConstraintValidatorContext constraintValidatorContext) {
		return longs.size() == 3;
	}
}
