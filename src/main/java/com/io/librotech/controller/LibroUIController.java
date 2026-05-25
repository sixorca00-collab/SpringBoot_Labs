package com.io.librotech.controller;

import com.io.librotech.models.Libro;
import com.io.librotech.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/ui/libros")
public class LibroUIController {

    @Autowired
    private BookService libroService;

    @GetMapping
    public String listarLibrosUI(Model model) {
        List<Libro> libros = libroService.getAll();
        model.addAttribute("libros", libros);
        model.addAttribute("tituloPantalla", "Catálogo de Libros - Dashboard");
        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("tituloPantalla", "Registrar Nuevo Libro");
        return "libros/formulario";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute("libro") Libro libro, Model model) {
        int anioActual = LocalDate.now().getYear();

        // 1. Validamos usando el nuevo campo LocalDate (manejando que no venga null)
        if (libro.getFechaPublicacion() != null && libro.getFechaPublicacion().getYear() > anioActual) {

            // 2. Corregida la concatenación rota del modelo
            model.addAttribute("ErrorAnio", "El año de publicación no puede ser mayor al año presente: " + anioActual);
            model.addAttribute("tituloPantalla", "Registrar nuevo libro (Corrección)");

            return "libros/formulario";
        }

        libroService.saveBook(libro);
        return "redirect:/ui/libros";
    }
}