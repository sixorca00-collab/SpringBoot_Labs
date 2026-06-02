package com.io.librotech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record LibroCreateDTO(
        String titulo,
        String autor,
        String isbn,
        LocalDate fechaPublicacion,
        BigDecimal precio,
        Long editorialId, // El cliente solo envía el ID de la editorial
        Set<Long> generoIds // El cliente envía los IDs de los géneros
) {}
