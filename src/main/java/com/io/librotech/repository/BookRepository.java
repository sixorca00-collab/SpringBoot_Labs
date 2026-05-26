package com.io.librotech.repository;


import com.io.librotech.models.Libro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.io.librotech.dto.LibroResumeDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Libro, Long> {
    // OPTIMIZACIÓN 1: Slice + JPQL Constructor
    // No hace SELECT COUNT, solo trae los datos necesarios.
    @Query("""
        SELECT new com.io.librotech.dto.LibroResumenDTO(
            l.id,
            l.titulo,
            l.fechaPublicacion,
            l.precio,
            l.editorial.nombre,
            l.editorial.pais
        )
        FROM Libro l
        JOIN l.editorial
        ORDER BY l.fechaPublicacion DESC
        """)
    Slice<LibroResumeDTO> findAllLibroResumenes(Pageable pageable);

    // OPTIMIZACIÓN 2: EntityGraph para evitar N+1 en detalles
    // Carga Editorial y Géneros en un solo JOIN FETCH automático.
    @EntityGraph(attributePaths = {"editorial", "generos"})
    Optional<Libro> findById(Long id);
}
