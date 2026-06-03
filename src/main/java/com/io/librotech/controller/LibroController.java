package com.io.librotech.controller;

import com.io.librotech.dto.LibroCreateDTO;
import com.io.librotech.dto.LibroResponseDTO;
import com.io.librotech.service.BookService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<LibroResponseDTO>> getAllBooks() {

        return ResponseEntity.ok(
                serviceBook.getAll()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(serviceBook.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro con ID " + id + " no encontrado.");
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<LibroResponseDTO> crear(@Valid @RequestBody LibroCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceBook.crearLibro(dto));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody LibroCreateDTO dto
    ) {
        try {
            return ResponseEntity.ok(serviceBook.actualizarLibro(id, dto));
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
    public ResponseEntity<Slice<LibroResponseDTO>> getCatalog(
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
