package com.io.librotech.service;

import com.io.librotech.dto.LibroResumeDTO;
import com.io.librotech.models.Libro;
import com.io.librotech.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<LibroResumeDTO> getAll() {
        return bookRepository.findAllLibroResumenes();
    }

    public Libro saveBook(Libro book) {
        return bookRepository.save(book);
    }

    public Libro obtenerPorId(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Libro no encontrado"));
    }

    public LibroResumeDTO obtenerResumenPorId(Long id) {
        Libro libro = bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Libro no encontrado"));

        return new LibroResumeDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getFechaPublicacion(),
                libro.getPrecio(),
                libro.getEditorial().getNombre(),
                libro.getEditorial().getPais()
        );
    }

    public Libro actualizar(Long id, Libro libroActualizado) {

        Libro libroExistente = bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Libro no encontrado"));

        libroExistente.setTitulo(libroActualizado.getTitulo());
        libroExistente.setAutor(libroActualizado.getAutor());
        libroExistente.setIsbn(libroActualizado.getIsbn());
        libroExistente.setFechaPublicacion(libroActualizado.getFechaPublicacion());
        libroExistente.setPrecio(libroActualizado.getPrecio());
        libroExistente.setDisponible(libroActualizado.getDisponible());
        libroExistente.setEditorial(libroActualizado.getEditorial());
        libroExistente.setGeneros(libroActualizado.getGeneros());

        return bookRepository.save(libroExistente);
    }

    public void eliminar(Long id) {

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado");
        }

        bookRepository.deleteById(id);
    }

    public Slice<LibroResumeDTO> getCatalog(int page, int size) {

        int validateSize = Math.min(size, 50);

        return bookRepository.findAllLibroResumenes(
                PageRequest.of(page, validateSize)
        );
    }

    public Libro getLibroConRelaciones(Long id) {

        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Libro no encontrado"));
    }

    @Transactional
    public void descatalogarLibro(Long id) {

        Libro libro = bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Libro no encontrado"));

        libro.softDelete();
    }
}
