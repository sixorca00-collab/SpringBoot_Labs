package com.io.librotech.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "editoriales")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@ToString
@EqualsAndHashCode

public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String pais;

    @Column(name = "fundado_en")
    private Integer fundadoEn;

    @OneToMany(
            mappedBy = "editorial",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )

    @JsonIgnore

    @ToString.Exclude
    @EqualsAndHashCode.Exclude

    private List<Libro> libros;
}