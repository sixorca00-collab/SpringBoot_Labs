package com.io.librotech.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ValidISBN, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        boolean empiezaCorrecto = value.startsWith("ISBN-") || value.startsWith("978-");
        boolean longitudCorrecta = value.length() >= 9 && value.length() <= 17;

        return empiezaCorrecto && longitudCorrecta;
    }
}
