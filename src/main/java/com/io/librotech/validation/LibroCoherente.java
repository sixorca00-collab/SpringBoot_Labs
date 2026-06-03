package com.io.librotech.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = LibroCoherenteValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LibroCoherente {

    String message() default "Si la editorial es la 1, el precio no puede ser menor a 20000";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
