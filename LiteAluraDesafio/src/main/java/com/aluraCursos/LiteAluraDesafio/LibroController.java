package com.aluraCursos.LiteAluraDesafio;

import com.aluraCursos.LiteAluraDesafio.model.Libros;
import com.aluraCursos.LiteAluraDesafio.model.DatosLibros;
import com.aluraCursos.LiteAluraDesafio.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;  // Importación de anotaciones REST
import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping("/guardar")
    public ResponseEntity<Libros> guardarLibro(@RequestBody DatosLibros datosLibros) {
        Libros libroGuardado = libroService.guardarLibro(datosLibros);
        return ResponseEntity.status(HttpStatus.CREATED).body(libroGuardado);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Libros>> buscarLibrosPorTitulo(@RequestParam String titulo) {
        List<Libros> libros = libroService.obtenerTodos();
        // Puedes filtrar según el parámetro o utilizar algún método de repositorio específico
        return ResponseEntity.ok(libros);
    }
}
