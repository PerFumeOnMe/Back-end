package PerfumeOnMe.spring.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import PerfumeOnMe.spring.validation.validator.ValidEnumKeywordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidEnumKeywordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnumKeyword {
	String message() default "올바르지 않은 키워드입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	// enum 클래스 명시
	Class<? extends Enum<?>> enumClass();
}
