package com.io.librotech.mapper;

import com.io.librotech.dto.LibroCreateDTO;
import com.io.librotech.dto.LibroResponseDTO;
import com.io.librotech.models.Libro;
import com.io.librotech.models.Genero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring") // Indica a Spring que inyecte este mapper como un @Component
public interface LibroMapper {

    // === DE ENTIDAD A RESPONSE DTO ===
    @Mapping(source = "editorial.nombre", target = "nombreEditorial")
    @Mapping(source = "generos", target = "generos", qualifiedByName = "mapGenerosToNombres")
    LibroResponseDTO toResponseDTO(Libro libro);

    @Named("mapGenerosToNombres")
    default Set<String> mapGenerosToNombres(Collection<Genero> generos) {
        if (generos == null) return null;
        return generos.stream().map(Genero::getNombre).collect(Collectors.toSet());
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", constant = "true")
    @Mapping(target = "editorial", ignore = true)
    @Mapping(target = "generos", ignore = true)
    Libro toEntity(LibroCreateDTO dto);
}
