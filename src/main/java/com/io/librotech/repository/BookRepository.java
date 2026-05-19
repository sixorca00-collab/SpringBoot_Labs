package com.io.librotech.repository;


import com.io.librotech.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Libro, Long> {
//Consulta de libros por autor.
    List<Libro> findByAutor(String autor);
}
