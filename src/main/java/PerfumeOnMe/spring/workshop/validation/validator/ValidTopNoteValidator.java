package PerfumeOnMe.spring.workshop.validation.validator;

import java.util.Set;

import PerfumeOnMe.spring.workshop.validation.annotation.ValidTopNote;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTopNoteValidator implements ConstraintValidator<ValidTopNote, String> {
	private static final Set<String> VALID_TOP_NOTES = Set.of(
		"베르가못", "레몬", "오렌지", "자몽", "사과", "페퍼민트"
	);

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// null 또는 공백 체크
		if (value == null || value.trim().isEmpty()) {
			return false;
		}
		// 허용된 탑 노트 목록에 포함되는지 확인
		return VALID_TOP_NOTES.contains(value.trim());
	}
}
