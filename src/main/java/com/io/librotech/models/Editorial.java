package com.io.librotech.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "editoriales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre; // Sincronizado con la columna 'nombre' del V1

    @Column(nullable = false, length = 500)
    private String direccion; // Faltaba en la entidad

    @Column(nullable = false, length = 100)
    private String pais; // Faltaba en la entidad

    @Column(name = "fundado_en") // Sincronizado con 'fundado_en' del V1
    private Integer fundadoEn; // En camelCase estándar de Java

    @OneToMany(mappedBy = "editorial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros;
}