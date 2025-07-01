package PerfumeOnMe.spring.validation.validator;

import org.springframework.stereotype.Component;

import PerfumeOnMe.spring.validation.annotation.ExistUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExistUserValidator
	implements ConstraintValidator<ExistUser, Long> {

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		return false;
	}
}
