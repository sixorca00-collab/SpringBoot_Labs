package com.io.librotech.models;

public class Categoria {

    private Long id;
    private String nombre;
    private Genero categoria;

    public Categoria() {
    }

    public Categoria(Long id, String nombre, Genero categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Genero getCategoria() {
        return categoria;
    }

    public void setCategoria(Genero categoria) {
        this.categoria = categoria;
    }
}