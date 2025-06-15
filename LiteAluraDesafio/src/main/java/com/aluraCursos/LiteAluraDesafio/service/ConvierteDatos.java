package com.aluraCursos.LiteAluraDesafio.service;

import com.aluraCursos.LiteAluraDesafio.model.DatosLibros;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConvierteDatos implements IConvierteDatos{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DatosLibros> obtenerLista(String json, Class<DatosLibros> datosLibrosClass) {
        try {
            // IMPORTANTE: Asumiendo que el JSON que recibes es una lista de libros.
            // Si el JSON tiene una estructura envuelta en un objeto (ej. { "results": [ ... ] }),
            // deberías mapear primero a la clase Datos y luego obtener la lista "resultados".
            return objectMapper.readValue(
                    json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, datosLibrosClass)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

