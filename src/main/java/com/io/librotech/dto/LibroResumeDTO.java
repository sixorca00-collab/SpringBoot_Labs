package com.io.librotech.dto;

import java.time.LocalDate;

public record LibroResumeDTO(Long id, String titulo, LocalDate fechaPublicacion, Double precio, String editorialNombre, String pais) {
}
