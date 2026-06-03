package com.io.librotech.validation;

import com.io.librotech.dto.LibroCreateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class LibroCoherenteValidator implements ConstraintValidator<LibroCoherente, LibroCreateDTO> {

    private static final BigDecimal PRECIO_MINIMO_EDITORIAL_EXCLUSIVA = new BigDecimal("20000");

    @Override
    public boolean isValid(LibroCreateDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.editorialId() == null || value.precio() == null) {
            return true;
        }

        boolean editorialExclusiva = Long.valueOf(1L).equals(value.editorialId());
        boolean precioInsuficiente = value.precio().compareTo(PRECIO_MINIMO_EDITORIAL_EXCLUSIVA) < 0;

        return !(editorialExclusiva && precioInsuficiente);
    }
}
