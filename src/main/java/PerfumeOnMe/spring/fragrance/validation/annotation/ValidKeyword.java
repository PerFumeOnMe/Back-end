package PerfumeOnMe.spring.fragrance.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import PerfumeOnMe.spring.fragrance.validation.validator.ValidKeywordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidKeywordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidKeyword {
	String message() default "검색어를 2글자 이상 입력해주세요.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
