package com.io.librotech.dto;

import com.io.librotech.validation.LibroCoherente;
import com.io.librotech.validation.ValidISBN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@LibroCoherente
public record LibroCreateDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(min = 2, max = 255, message = "El título debe tener entre 2 y 255 caracteres")
        String titulo,

        @NotBlank(message = "El autor es obligatorio")
        @Size(min = 2, max = 255, message = "El autor debe tener entre 2 y 255 caracteres")
        String autor,

        @NotBlank(message = "El ISBN no puede estar vacío")
        @ValidISBN
        String isbn,

        @NotNull(message = "La fecha de publicación es obligatoria")
        @PastOrPresent(message = "La fecha de publicación no puede estar en el futuro")
        LocalDate fechaPublicacion,

        @NotNull(message = "El precio es obligatorio")
        @PositiveOrZero(message = "El precio no puede ser negativo")
        BigDecimal precio,

        @NotNull(message = "Debe especificar el ID de la editorial")
        @Positive(message = "ID de editorial inválido")
        Long editorialId, // El cliente solo envía el ID de la editorial

        @NotEmpty(message = "El libro debe pertenecer al menos a un género")
        Set<@NotNull(message = "El id del género no puede ser nulo")
                @Positive(message = "El id del género debe ser positivo") Long> generoIds // El cliente envía los IDs de los géneros
) {}
