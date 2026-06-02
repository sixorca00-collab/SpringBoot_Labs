package com.io.librotech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LibroFormDTO {
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate fechaPublicacion;
    private BigDecimal precio;
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
