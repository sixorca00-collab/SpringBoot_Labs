package com.io.librotech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record LibroResponseDTO(
        Long id,
        String titulo,
        String autor,
        String isbn,
        LocalDate fechaPublicacion,
        BigDecimal precio,
        String nombreEditorial, // Aplanamos la relación: solo enviamos el nombre
        Set<String> generos // Solo los nombres de los géneros
) {}
