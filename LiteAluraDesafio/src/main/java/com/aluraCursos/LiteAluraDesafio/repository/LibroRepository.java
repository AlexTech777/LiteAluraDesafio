package com.aluraCursos.LiteAluraDesafio.repository;

import com.aluraCursos.LiteAluraDesafio.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libros, Long> {

    Optional<Libros> findByTituloContainsIgnoreCase(String nombreLibro);

    List<Libros> findByTituloContainingIgnoreCase(String titulo);

    Optional<Libros> findByTituloIgnoreCase(String titulo);

    // Modificada la query para buscar en la cadena 'autores'
    @Query("SELECT l FROM Libros l WHERE LOWER(l.autores) LIKE LOWER(CONCAT('%', :autor, '%'))")
    List<Libros> findByAutorLike(@Param("autor") String autor);

    @Query("SELECT l FROM Libros l WHERE LOWER(l.idiomas) = LOWER(:idioma)")
    List<Libros> findByIdiomaIgnoreCase(@Param("idioma") String idioma);
}