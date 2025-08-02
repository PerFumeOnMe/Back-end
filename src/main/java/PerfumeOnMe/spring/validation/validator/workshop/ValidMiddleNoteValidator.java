package PerfumeOnMe.spring.validation.validator.workshop;

import java.util.Set;

import PerfumeOnMe.spring.validation.annotation.workshop.ValidMiddleNote;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMiddleNoteValidator implements ConstraintValidator<ValidMiddleNote, String> {

	private static final Set<String> VALID_MIDDLE_NOTES = Set.of(
		"장미", "자스민", "라벤더", "일랑일랑", "아이리스", "피오니"
	);

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty()) {
			return false;
		}

		return VALID_MIDDLE_NOTES.contains(value.trim());
	}
}