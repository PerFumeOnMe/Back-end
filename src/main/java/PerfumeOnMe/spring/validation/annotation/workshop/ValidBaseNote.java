package PerfumeOnMe.spring.validation.annotation.workshop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nimbusds.jose.Payload;

import PerfumeOnMe.spring.validation.validator.workshop.ValidBaseNoteValidator;
import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = ValidBaseNoteValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBaseNote {
	String message() default "베이스노트는 바닐라, 머스크, 샌달우드, 패츌리, 앰버, 시더우드 중 하나여야 합니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}