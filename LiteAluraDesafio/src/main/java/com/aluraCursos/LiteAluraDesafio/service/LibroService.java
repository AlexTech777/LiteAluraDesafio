package com.aluraCursos.LiteAluraDesafio.service;

import com.aluraCursos.LiteAluraDesafio.model.DatosLibros;
import com.aluraCursos.LiteAluraDesafio.model.Libros;
import com.aluraCursos.LiteAluraDesafio.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Libros guardarLibro(DatosLibros datosLibros) {
        Libros libro = new Libros(datosLibros);
        return libroRepository.save(libro);
    }

    public Optional<Libros> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainsIgnoreCase(titulo);
    }

    public List<Libros> obtenerTodos() {
        return libroRepository.findAll();
    }

    public Map<String, Long> obtenerEstadisticasPorIdioma() {
        return Map.of();
    }

    public List<Libros> obtenerLibrosPorIdioma(String idioma) {
        List<Libros> resultado = libroRepository.findByIdiomaIgnoreCase(idioma);
        System.out.println("🔍 Libros encontrados para idioma '" + idioma + "': " + resultado.size());
        return resultado;
    }
}