package com.io.librotech.dto;

import com.io.librotech.validation.ValidISBN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LibroFormDTO {
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 2, max = 255, message = "El título debe tener entre 2 y 255 caracteres")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(min = 2, max = 255, message = "El autor debe tener entre 2 y 255 caracteres")
    private String autor;

    @NotBlank(message = "El ISBN no puede estar vacío")
    @ValidISBN
    private String isbn;

    @NotNull(message = "La fecha de publicación es obligatoria")
    @PastOrPresent(message = "La fecha de publicación no puede estar en el futuro")
    private LocalDate fechaPublicacion;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private BigDecimal precio;

    @NotNull(message = "Debe especificar el ID de la editorial")
    @Positive(message = "ID de editorial inválido")
    private Long editorialId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Long getEditorialId() {
        return editorialId;
    }

    public void setEditorialId(Long editorialId) {
        this.editorialId = editorialId;
    }
}
