package com.io.librotech.controller;

import com.io.librotech.models.Editorial;
import com.io.librotech.models.Libro;
import com.io.librotech.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.io.librotech.dto.LibroResumeDTO;

import java.time.LocalDate;

@Controller
@RequestMapping("/ui/libros")
public class LibroUIController {

    @Autowired
    private BookService libroService;

    @GetMapping
    public String listarLibrosUI(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            Model model) {

        Slice<LibroResumeDTO> slice = libroService.getCatalog(page, size);

        model.addAttribute("libros", slice.getContent());
        model.addAttribute("currentPage", slice.getNumber());
        model.addAttribute("hasNext", slice.hasNext());
        model.addAttribute("hasPrevious", slice.hasPrevious());

        model.addAttribute("tituloPantalla", "Catálogo de Libros - Dashboard");

        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        Libro libro = new Libro();
        libro.setEditorial(new Editorial());
        model.addAttribute("libro", libro);
        model.addAttribute("tituloPantalla", "Registrar Nuevo Libro");
        return "libros/formulario";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute("libro") Libro libro, Model model) {
        int anioActual = LocalDate.now().getYear();

        // 1. Validamos usando el nuevo campo LocalDate (manejando que no venga null)
        if (libro.getFechaPublicacion() != null && libro.getFechaPublicacion().getYear() > anioActual) {
            if (libro.getEditorial() == null) {
                libro.setEditorial(new Editorial());
            }

            // 2. Corregida la concatenación rota del modelo
            model.addAttribute("ErrorAnio", "El año de publicación no puede ser mayor al año presente: " + anioActual);
            model.addAttribute("tituloPantalla", "Registrar nuevo libro (Corrección)");

            return "libros/formulario";
        }

        libroService.saveBook(libro);
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
