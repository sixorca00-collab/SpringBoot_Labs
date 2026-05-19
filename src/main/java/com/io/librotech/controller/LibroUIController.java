package com.io.librotech.controller;

import com.io.librotech.models.Libro;
import com.io.librotech.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller // Ojo: Aquí NO usamos @RestController, solo @Controller
@RequestMapping("/admin/libros")
public class LibroUIController {

    @Autowired
    private BookService libroService; // Tu servicio de siempre

    @GetMapping
    public String listarLibrosUI(Model model) {
        // 1. Buscamos los libros que ya existen en la base de datos
        List<Libro> libros = libroService.getAll();

        // 2. Metemos los datos en la "maleta" (Model) para que el HTML los pueda ver
        model.addAttribute("libros", libros);
        model.addAttribute("tituloPantalla", "Catálogo de Libros - Dashboard");

        // 3. Le decimos a Spring que busque el archivo HTML llamado "lista" dentro de la carpeta "libros"
        return "libros/lista";
    }
}