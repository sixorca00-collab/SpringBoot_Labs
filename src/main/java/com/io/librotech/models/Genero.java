package com.io.librotech.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "generos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre; // Sincronizado con 'nombre' del V1

    @Column(length = 500)
    private String descripcion; // Sincronizado con 'descripcion' del V1

    // Relación ManyToMany hacia Libro.
    // Usamos 'mappedBy = "generos"' porque el dueño de la relación será la entidad Libro.
    @ManyToMany(mappedBy = "generos", fetch = FetchType.LAZY)
    private List<Libro> libros;
}