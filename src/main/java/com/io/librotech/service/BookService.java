package com.io.librotech.service;

import com.io.librotech.dto.LibroCreateDTO;
import com.io.librotech.dto.LibroResponseDTO;
import com.io.librotech.dto.LibroResumeDTO;
import com.io.librotech.mapper.LibroMapper;
import com.io.librotech.models.Editorial;
import com.io.librotech.models.Genero;
import com.io.librotech.models.Libro;
import com.io.librotech.repository.BookRepository;
import com.io.librotech.repository.EditorialRepository;
import com.io.librotech.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public List<LibroResumeDTO> getAll() {
        return libroRepository.findAllLibroResumenes();
    }

    public LibroResumeDTO obtenerResumenPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return new LibroResumeDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getFechaPublicacion(),
                libro.getPrecio(),
                libro.getEditorial() != null ? libro.getEditorial().getNombre() : null,
                libro.getEditorial() != null ? libro.getEditorial().getPais() : null
        );
    }

    @Transactional
    public Libro saveBook(Libro libro) {
        Libro libroAguardar = prepararLibroParaPersistencia(libro);
        return libroRepository.save(libroAguardar);
    }

    @Transactional
    public Libro actualizar(Long id, Libro libro) {
        Libro existente = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        existente.setTitulo(libro.getTitulo());
        existente.setAutor(libro.getAutor());
        existente.setIsbn(libro.getIsbn());
        existente.setFechaPublicacion(libro.getFechaPublicacion());
        existente.setPrecio(libro.getPrecio());
        existente.setDisponible(libro.getDisponible() != null ? libro.getDisponible() : existente.getDisponible());
        existente.setEditorial(resolverEditorial(libro.getEditorial()));
        if (libro.getGeneros() != null) {
            existente.setGeneros(resolverGeneros(libro.getGeneros()));
        }

        return libroRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado");
        }
        libroRepository.deleteById(id);
    }

    public Slice<LibroResumeDTO> getCatalog(int page, int size) {
        int validateSize = Math.min(size, 50);
        return libroRepository.findAllLibroResumenes(PageRequest.of(page, validateSize));
    }

    @Transactional
    public void descatalogarLibro(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        libro.softDelete();
        libroRepository.save(libro);
    }

    @Transactional
    public LibroResponseDTO crearLibro(LibroCreateDTO dto) {
        Libro libro = libroMapper.toEntity(dto);
        libro.setPrecio(dto.precio());

        if (dto.editorialId() == null) {
            throw new RuntimeException("Editorial no existe");
        }
        Editorial editorial = editorialRepository.findById(dto.editorialId())
                .orElseThrow(() -> new RuntimeException("Editorial no existe"));
        libro.setEditorial(editorial);

        List<Genero> generos = cargarGenerosPorIds(dto.generoIds());
        libro.setGeneros(generos);

        Libro libroGuardado = libroRepository.save(libro);
        return libroMapper.toResponseDTO(libroGuardado);
    }

    private Libro prepararLibroParaPersistencia(Libro libro) {
        if (libro == null) {
            throw new RuntimeException("Libro inválido");
        }

        libro.setEditorial(resolverEditorial(libro.getEditorial()));
        if (libro.getGeneros() != null) {
            libro.setGeneros(resolverGeneros(libro.getGeneros()));
        }
        return libro;
    }

    private Editorial resolverEditorial(Editorial editorial) {
        if (editorial == null) {
            return null;
        }
        if (editorial.getId() == null) {
            return editorialRepository.save(editorial);
        }
        return editorialRepository.findById(editorial.getId())
                .orElseThrow(() -> new RuntimeException("Editorial no existe"));
    }

    private List<Genero> resolverGeneros(Collection<Genero> generos) {
        if (generos == null) {
            return null;
        }
        List<Long> ids = generos.stream()
                .map(Genero::getId)
                .filter(Objects::nonNull)
                .toList();
        return cargarGenerosPorIds(ids);
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
}
