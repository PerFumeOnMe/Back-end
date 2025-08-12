package PerfumeOnMe.spring.imagekeyword.validation.validator;

import java.lang.reflect.Method;
import java.util.Arrays;

import PerfumeOnMe.spring.imagekeyword.validation.annotation.ValidEnumKeyword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEnumKeywordValidator implements ConstraintValidator<ValidEnumKeyword, String> {

	private Class<? extends Enum<?>> enumClass;

	@Override
	public void initialize(ValidEnumKeyword constraintAnnotation) {
		this.enumClass = constraintAnnotation.enumClass();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty())
			return false;

		return Arrays.stream(enumClass.getEnumConstants())
			.anyMatch(e -> {
				try {
					Method getDisplayName = enumClass.getMethod("getDisplayName");
					String displayName = (String)getDisplayName.invoke(e);
					return displayName.equals(value);
				} catch (Exception ex) {
					return false;
				}
			});
	}
}
