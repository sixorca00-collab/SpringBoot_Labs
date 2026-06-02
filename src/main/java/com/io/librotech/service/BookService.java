package com.io.librotech.service;

import com.io.librotech.dto.LibroCreateDTO;
import com.io.librotech.dto.LibroResponseDTO;
import com.io.librotech.mapper.LibroMapper;
import com.io.librotech.models.Editorial;
import com.io.librotech.models.Genero;
import com.io.librotech.models.Libro;
import com.io.librotech.repository.BookRepository;
import com.io.librotech.repository.EditorialRepository;
import com.io.librotech.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository libroRepository;
    private final EditorialRepository editorialRepository;
    private final GeneroRepository generoRepository;
    private final LibroMapper libroMapper;

    public LibroResponseDTO obtenerPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return libroMapper.toResponseDTO(libro);
    }

    public List<LibroResponseDTO> getAll() {
        return libroRepository.findAll().stream()
                .map(libroMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public LibroResponseDTO crearLibro(LibroCreateDTO dto) {
        Libro libro = new Libro();
        aplicarDatosDelDto(libro, dto, true);
        return libroMapper.toResponseDTO(libroRepository.save(libro));
    }

    @Transactional
    public LibroResponseDTO actualizarLibro(Long id, LibroCreateDTO dto) {
        Libro existente = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        aplicarDatosDelDto(existente, dto, false);
        return libroMapper.toResponseDTO(libroRepository.save(existente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado");
        }
        libroRepository.deleteById(id);
    }

    public Slice<LibroResponseDTO> getCatalog(int page, int size) {
        int validateSize = Math.min(size, 50);
        Page<Libro> libros = libroRepository.findAll(PageRequest.of(page, validateSize));
        return new SliceImpl<>(
                libros.getContent().stream().map(libroMapper::toResponseDTO).toList(),
                libros.getPageable(),
                libros.hasNext()
        );
    }

    @Transactional
    public void descatalogarLibro(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        libro.softDelete();
        libroRepository.save(libro);
    }

    private List<Genero> cargarGenerosPorIds(Collection<Long> ids) {
        if (ids == null) {
            return new ArrayList<>();
        }
        List<Long> idsNormalizados = ids.stream()
                .filter(Objects::nonNull)
                .toList();
        if (idsNormalizados.isEmpty()) {
            return new ArrayList<>();
        }
        List<Genero> generos = generoRepository.findAllById(idsNormalizados).stream().toList();
        if (generos.size() != idsNormalizados.size()) {
            throw new RuntimeException("Uno o más géneros no existen");
        }
        return new ArrayList<>(generos);
    }

    private void aplicarDatosDelDto(Libro libro, LibroCreateDTO dto, boolean crear) {
        if (dto == null) {
            throw new RuntimeException("Libro inválido");
        }

        libro.setTitulo(dto.titulo());
        libro.setAutor(dto.autor());
        libro.setIsbn(dto.isbn());
        libro.setFechaPublicacion(dto.fechaPublicacion());
        libro.setPrecio(dto.precio());
        libro.setDisponible(crear ? Boolean.TRUE : libro.getDisponible());
        libro.setEditorial(resolverEditorialPorId(dto.editorialId()));

        if (crear || dto.generoIds() != null) {
            libro.setGeneros(cargarGenerosPorIds(dto.generoIds()));
        }
    }

    private Editorial resolverEditorialPorId(Long editorialId) {
        if (editorialId == null) {
            throw new RuntimeException("Editorial no existe");
        }
        return editorialRepository.findById(editorialId)
                .orElseThrow(() -> new RuntimeException("Editorial no existe"));
    }
}
