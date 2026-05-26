package com.io.librotech.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "libros")
@Data
@NoArgsConstructor
@AllArgsConstructor

@SQLRestriction("disponible = true")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 200)
    private String autor;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion; // Mapeado con DATE del V1

    private Double precio;

    @Column(nullable = false)
    private Boolean disponible = true; // Valor por defecto mapeado con el V1

    // Relación Muchos a Uno con Editorial
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editorial_id", nullable = false) // Llave foránea en la BD
    private Editorial editorial;

    // Relación Muchos a Muchos con Genero (Dueño de la relación)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libros_generos", // Nombre de la tabla intermedia (V2)
            joinColumns = @JoinColumn(name = "libro_id"), // Llave que apunta a esta tabla (Libro)
            inverseJoinColumns = @JoinColumn(name = "genero_id") // Llave que apunta a la otra tabla (Genero)
    )
    private List<Genero> generos;
}