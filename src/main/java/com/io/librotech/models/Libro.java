package com.io.librotech.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "libros")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@ToString
@EqualsAndHashCode

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String autor;

    private String isbn;

    private LocalDate fechaPublicacion;

    private BigDecimal precio;

    private Boolean disponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editorial_id")

    @ToString.Exclude
    @EqualsAndHashCode.Exclude

    private Editorial editorial;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libros_generos",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )

    @ToString.Exclude
    @EqualsAndHashCode.Exclude

    private List<Genero> generos;

    public void softDelete() {
        this.disponible = false;
    }
}