package com.io.librotech.service;


import com.io.librotech.models.Libro;
import com.io.librotech.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {

    private BookRepository bookRepository;


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
            libroExistente.setAnioPublicacion(libroActualizado.getAnioPublicacion());
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
}
