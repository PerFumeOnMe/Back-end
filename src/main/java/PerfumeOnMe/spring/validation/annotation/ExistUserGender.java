package PerfumeOnMe.spring.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import PerfumeOnMe.spring.validation.validator.ExistUserGenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistUserGenderValidator.class)
@Documented
public @interface ExistUserGender {
	String message() default "MALE, FEMALE, NONE 중 하나만 입력할 수 있습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
