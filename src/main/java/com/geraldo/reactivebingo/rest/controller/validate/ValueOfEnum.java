package com.geraldo.reactivebingo.rest.controller.validate;

import com.geraldo.reactivebingo.rest.controller.validate.impl.ValueOfEnumImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValueOfEnumImpl.class)
@Retention(RUNTIME)
@Target(PARAMETER)
@ReportAsSingleViolation
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Value is not valid";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
