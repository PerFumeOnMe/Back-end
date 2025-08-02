package PerfumeOnMe.spring.validation.validator.workshop;

import java.util.Set;

import PerfumeOnMe.spring.validation.annotation.workshop.ValidBaseNote;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBaseNoteValidator implements ConstraintValidator<ValidBaseNote, String> {

	private static final Set<String> VALID_BASE_NOTES = Set.of(
		"바닐라", "머스크", "샌달우드", "패츌리", "앰버", "시더우드"
	);

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty()) {
			return false;
		}

		return VALID_BASE_NOTES.contains(value.trim());
	}
}