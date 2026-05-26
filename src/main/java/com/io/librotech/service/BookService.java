package com.io.librotech.service;

import com.io.librotech.dto.LibroResumeDTO;
import com.io.librotech.models.Libro;
import com.io.librotech.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository; // Es buena práctica marcarlo como final con Lombok

    public List<Libro> getAll(){
        return bookRepository.findAll();
    }

    public Libro saveBook(Libro book){
        return bookRepository.save(book);
    }

    public Optional<Libro> obtenerPorId(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Libro> actualizar(Long id, Libro libroActualizado) {
        return bookRepository.findById(id).map(libroExistente -> {
            libroExistente.setTitulo(libroActualizado.getTitulo());
            libroExistente.setAutor(libroActualizado.getAutor());
            libroExistente.setIsbn(libroActualizado.getIsbn());

            // 1. Corregido el 'get' por 'set' para la nueva fecha LocalDate
            libroExistente.setFechaPublicacion(libroActualizado.getFechaPublicacion());

            // 2. Agregamos el precio (que está en el modelo)
            libroExistente.setPrecio(libroActualizado.getPrecio());
            libroExistente.setDisponible(libroActualizado.getDisponible());

            // 3. Sincronizamos las nuevas relaciones para que se puedan editar
            libroExistente.setEditorial(libroActualizado.getEditorial());
            libroExistente.setGeneros(libroActualizado.getGeneros());

            return bookRepository.save(libroExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Slice<LibroResumeDTO> getCatalog(int page, int size){
        //max 50 registros
        int validateSize = Math.min(size, 50);
        return bookRepository.findAllLibroResumenes(PageRequest.of(page, validateSize));
    }

    public Libro getLibroConRelaciones(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("libro no encontrado"));
    }


}