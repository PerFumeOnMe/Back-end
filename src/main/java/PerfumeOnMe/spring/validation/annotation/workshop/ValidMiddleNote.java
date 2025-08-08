package PerfumeOnMe.spring.validation.annotation.workshop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import PerfumeOnMe.spring.validation.validator.workshop.ValidMiddleNoteValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidMiddleNoteValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMiddleNote {
	String message() default "미들노트는 장미, 자스민, 라벤더, 일랑일랑, 아이리스, 피오니 중 하나여야 합니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
