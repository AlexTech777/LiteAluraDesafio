package com.aluraCursos.LiteAluraDesafio.service;

import com.aluraCursos.LiteAluraDesafio.model.DatosLibros;
import java.util.List;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
    List<DatosLibros> obtenerLista(String json, Class<DatosLibros> datosLibrosClass);
}