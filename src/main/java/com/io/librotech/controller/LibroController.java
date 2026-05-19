package com.io.librotech.controller;

import com.io.librotech.models.Libro;
import com.io.librotech.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/libros")
@AllArgsConstructor

public class LibroController {

    private BookService serviceBook;
    //GET
    @GetMapping
    public List<Libro> getAllBooks(){
        return serviceBook.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Libro> libro = serviceBook.obtenerPorId(id);
        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get()); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Libro con ID " + id + " no encontrado."); // 404
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        Libro nuevoLibro = serviceBook.saveBook(libro);
        return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED); // 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        Optional<Libro> actualizado = serviceBook.actualizar(id, libro);
        if (actualizado.isPresent()) {
            return ResponseEntity.ok(actualizado.get()); // 200
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se pudo actualizar. Libro no encontrado."); // 404
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (serviceBook.eliminar(id)) {
            return ResponseEntity.noContent().build() ;  // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404
    }

}
