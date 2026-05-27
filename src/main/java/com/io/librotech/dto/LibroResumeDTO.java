package com.io.librotech.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public record LibroResumeDTO(Long id, String titulo, LocalDate fechaPublicacion, BigDecimal precio, String editorialNombre, String pais) {
}
