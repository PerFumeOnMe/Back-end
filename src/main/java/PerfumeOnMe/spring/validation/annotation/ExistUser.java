package PerfumeOnMe.spring.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import PerfumeOnMe.spring.validation.validator.ExistUserValidator;
import jakarta.validation.Constraint;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistUserValidator.class)
@Documented
public @interface ExistUser {
}
