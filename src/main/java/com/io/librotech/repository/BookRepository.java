package com.io.librotech.repository;


import com.io.librotech.models.Libro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Libro, Long> {
    @Override
    @EntityGraph(attributePaths = {"editorial", "generos"})
    List<Libro> findAll();

    @Override
    @EntityGraph(attributePaths = {"editorial", "generos"})
    Page<Libro> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"editorial", "generos"})
    Optional<Libro> findById(Long id);
}
