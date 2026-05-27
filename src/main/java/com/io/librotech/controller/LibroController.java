package com.io.librotech.controller;

import com.io.librotech.dto.LibroResumeDTO;
import com.io.librotech.models.Libro;
import com.io.librotech.service.BookService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {

    private final BookService serviceBook;

    // GET ALL
    @GetMapping
    public ResponseEntity<List<LibroResumeDTO>> getAllBooks() {

        return ResponseEntity.ok(
                serviceBook.getAll()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {

        try {

            LibroResumeDTO libro = serviceBook.obtenerResumenPorId(id);

            return ResponseEntity.ok(libro);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro con ID " + id + " no encontrado.");
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {

        Libro nuevoLibro = serviceBook.saveBook(libro);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevoLibro);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody Libro libro
    ) {

        try {

            Libro actualizado = serviceBook.actualizar(id, libro);

            return ResponseEntity.ok(actualizado);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se pudo actualizar. Libro no encontrado.");
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        try {

            serviceBook.eliminar(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado.");
        }
    }

    // CATALOGO OPTIMIZADO
    @GetMapping("/catalogo")
    public ResponseEntity<Slice<LibroResumeDTO>> getCatalog(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(
                serviceBook.getCatalog(page, size)
        );
    }

    // SOFT DELETE
    @PatchMapping("/{id}/descatalogar")
    public ResponseEntity<?> descatalogarLibro(
            @PathVariable Long id
    ) {

        try {

            serviceBook.descatalogarLibro(id);

            return ResponseEntity.ok("Libro descatalogado");

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado.");
        }
    }
}
