package com.io.librotech.controller;

import com.io.librotech.dto.LibroCreateDTO;
import com.io.librotech.dto.LibroFormDTO;
import com.io.librotech.dto.LibroResponseDTO;
import com.io.librotech.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/ui/libros")
@RequiredArgsConstructor
public class LibroUIController {
    private final BookService libroService;

    @GetMapping
    public String listarLibrosUI(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            Model model) {

        Slice<LibroResponseDTO> slice = libroService.getCatalog(page, size);

        model.addAttribute("libros", slice.getContent());
        model.addAttribute("currentPage", slice.getNumber());
        model.addAttribute("pageSize", size);
        model.addAttribute("hasNext", slice.hasNext());
        model.addAttribute("hasPrevious", slice.hasPrevious());

        model.addAttribute("tituloPantalla", "Catálogo de Libros - Dashboard");

        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("libro", new LibroFormDTO());
        model.addAttribute("tituloPantalla", "Registrar Nuevo Libro");
        return "libros/formulario";
    }

    @PostMapping("/guardar")
    public String guardarLibro(
            @Valid @ModelAttribute("libro") LibroFormDTO libro,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tituloPantalla", "Registrar Nuevo Libro");
            return "libros/formulario";
        }

        libroService.crearLibro(new LibroCreateDTO(
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn(),
                libro.getFechaPublicacion(),
                libro.getPrecio(),
                libro.getEditorialId(),
                Collections.emptySet()
        ));
        return "redirect:/ui/libros";
    }

    @PostMapping("/{id}/descatalogar")
    public String descatalogarLibro(@PathVariable Long id,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "50") int size) {
        libroService.descatalogarLibro(id);
        return "redirect:/ui/libros?page=" + page + "&size=" + size;
    }
}
