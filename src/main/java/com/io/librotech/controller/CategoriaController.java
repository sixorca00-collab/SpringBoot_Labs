package com.io.librotech.controller;

import com.io.librotech.models.Categoria;
import com.io.librotech.models.Genero;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private List<Categoria> categorias = new ArrayList<>();

    @GetMapping
    public List<Categoria> listarCategorias() {
        return categorias;
    }

    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) {

        categoria.setId((long) (categorias.size() + 1));

        categorias.add(categoria);

        return categoria;
    }
}