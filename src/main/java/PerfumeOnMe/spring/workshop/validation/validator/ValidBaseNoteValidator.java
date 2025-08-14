package PerfumeOnMe.spring.workshop.validation.validator;

import java.util.Set;

import PerfumeOnMe.spring.workshop.validation.annotation.ValidBaseNote;
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