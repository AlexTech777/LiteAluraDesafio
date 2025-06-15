package com.aluraCursos.LiteAluraDesafio.principal;

import com.aluraCursos.LiteAluraDesafio.model.Datos;
import com.aluraCursos.LiteAluraDesafio.model.DatosAutor;
import com.aluraCursos.LiteAluraDesafio.model.DatosLibros;
import com.aluraCursos.LiteAluraDesafio.model.Libros;
import com.aluraCursos.LiteAluraDesafio.repository.LibroRepository;
import com.aluraCursos.LiteAluraDesafio.service.ConsumoApi;
import com.aluraCursos.LiteAluraDesafio.service.ConvierteDatos;
import com.aluraCursos.LiteAluraDesafio.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class Principal {

    private final String URL_BASE = "https://gutendex.com/books/";
    private final LibroRepository repositorio;
    private final ConsumoApi consumoApi;
    private final ConvierteDatos conversor;
    private final java.util.Scanner teclado = new java.util.Scanner(System.in);
    private final LibroService libroService;


    @Autowired
    public Principal(LibroRepository repositorio, LibroService libroService) {
        this.repositorio = repositorio;
        this.libroService = libroService;
        // Se instancian manualmente; alternativamente podrías inyectarlos
        this.consumoApi = new ConsumoApi();
        this.conversor = new ConvierteDatos();
    }

    public void muestraElMenu() {
        int opcion;
        do {
            System.out.println("""
        ╔═════════════════════════════════════╗
        ║      📚 MENÚ PRINCIPAL 📚          ║
        ╠════════════════════════════════════╣
        ║ 1️⃣  🔍 Buscar Libro por Título     ║
        ║ 2️⃣  📖 Listar Libros Registrados   ║
        ║ 3️⃣  ✍️  Listar Autores Registrados ║
        ║ 4️⃣  🏛️  Listar Autores por Años    ║
        ║ 5️⃣  🌍 Listar Libros por Idioma    ║
        ║ 0️⃣  ❌ Salir                       ║
        ╚════════════════════════════════════╝
        """);

            System.out.print("\n🟢 Selecciona una opción: ");
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1 -> {
                        var libro = buscarLibroTitulo();
                        if (libro == null) {
                            System.out.println("Volviendo al menú principal...");
                        }
                    }
                    case 2 -> mostrarLibrosRegistrados();
                    case 3 -> buscarLibroAutores();
                    case 4 -> buscarLibrosAutoresVivos();
                    case 5 -> buscarLibrosPorIdioma();
                    case 0 -> System.out.println("🔴 Cerrando sistema...");
                    default -> System.out.println("⚠️ Opción inválida.");
                }
            } else {
                System.out.println("❌ Entrada inválida, por favor ingresa un número.");
                teclado.nextLine(); // Limpiar entrada incorrecta
                opcion = -1;
            }
        } while (opcion != 0);
    }

    private DatosLibros buscarLibroTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var tituloLibro = teclado.nextLine().trim();

        try {
            String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
            System.out.println("JSON recibido: " + json);

            if (json.equals("{}")) {
                System.out.println("❌ No se encontraron datos para el libro.");
                return null;
            }

            Datos datos = conversor.obtenerDatos(json, Datos.class);

            if (datos.resultados().isEmpty()) {
                System.out.println("❌ No se encontraron libros con ese título.");
                return null;
            }

            DatosLibros libroEncontrado = datos.resultados().stream().findFirst().orElse(null);
            if (libroEncontrado == null) {
                System.out.println("❌ Error al obtener datos del libro.");
                return null;
            }

            System.out.println("\nLibro encontrado:");
            System.out.println("Título: " + libroEncontrado.titulo());
            System.out.println("Autor(es): " + libroEncontrado.autor());
            System.out.println("Idiomas disponibles: " + libroEncontrado.idiomas());
            System.out.println("Descripción: " + libroEncontrado.descripcion());
            System.out.println("\n--------------------------------------\n");

            Optional<Libros> libroRegistradoOpt = repositorio.findByTituloIgnoreCase(libroEncontrado.titulo().trim());
            if (libroRegistradoOpt.isPresent()) {
                System.out.println("✅ El libro ya está registrado en la base de datos.");
            } else {
                Libros libroDb = new Libros(libroEncontrado);
                Libros libroGuardado = repositorio.save(libroDb);
                System.out.println("✅ Libro registrado en la base de datos con ID: " + libroGuardado.getId());
            }

            return libroEncontrado;
        } catch (Exception e) {
            System.out.println("❌ Ocurrió un error al buscar el libro: " + e.getMessage());
            return null;
        }
    }
    private void mostrarLibrosRegistrados() {
        var libros = repositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("Libros registrados:");
            for (var libro : libros) {
                System.out.println("ID: " + libro.getId() +
                        " | Título: " + libro.getTitulo() +
                        " | Autores: " + libro.getAutores() +
                        " | Descripción: " + libro.getDescripcion());

            }
        }
    }

    private void buscarLibroAutores() {
        System.out.println("Escribe el Apellido del autor que deseas buscar:");
        String autorBusqueda = teclado.nextLine().trim();

        // Se utiliza la query modificada para campos almacenados como cadena
        List<Libros> librosPorAutor = repositorio.findByAutorLike(autorBusqueda);

        if (librosPorAutor.isEmpty()) {
            System.out.println("❌ No se encontraron libros con autor que contenga: " + autorBusqueda);
        } else {
            System.out.println("Libros encontrados para el autor '" + autorBusqueda + "':");
            for (Libros libro : librosPorAutor) {
                System.out.println("ID: " + libro.getId()
                        + " | Título: " + libro.getTitulo()
                        + " | Autores: " + libro.getAutores());
            }
        }
    }
    private void buscarLibrosAutoresVivos() {
        try {
            // Solicitar al usuario el rango de años (para el año de nacimiento)
            System.out.println("Ingrese el año inicial del rango (para Birth Year):");
            int anioInicio = Integer.parseInt(teclado.nextLine().trim());
            System.out.println("Ingrese el año final del rango (para Birth Year):");
            int anioFin = Integer.parseInt(teclado.nextLine().trim());

            // Obtener todos los libros registrados desde la base de datos
            List<Libros> libros = libroService.obtenerTodos();

            // Imprimir los valores antes del filtro para depuración
            libros.forEach(libro -> System.out.println("Libro: " + libro.getTitulo() + " | Birth Year: " + libro.getBirthYear()));

            // Filtrar libros con autores en el rango de años especificado
            List<Libros> librosFiltrados = libros.stream()
                    .filter(libro -> libro.getBirthYear() != null && libro.getBirthYear() > 0) // Filtra valores inválidos
                    .peek(libro -> System.out.println("Filtrando: " + libro.getTitulo() + " | Birth Year: " + libro.getBirthYear())) // Depuración
                    .filter(libro -> libro.getBirthYear() >= anioInicio && libro.getBirthYear() <= anioFin) // Aplica el rango de años
                    .toList();

            // Mostrar resultados
            if (librosFiltrados.isEmpty()) {
                System.out.println("No se encontraron libros de autores nacidos entre " + anioInicio + " y " + anioFin);
            } else {
                System.out.println("Libros encontrados para autores nacidos entre " + anioInicio + " y " + anioFin + ":");
                for (Libros libro : librosFiltrados) {
                    System.out.println(" Autor(es): " + String.join(", ", libro.getAutores()+ " | Birth Year: " + libro.getBirthYear()));
                }
            }

        } catch (Exception e) {
            System.out.println("Error al procesar el rango de años: " + e.getMessage());
        }
    }
    private void buscarLibrosPorIdioma() {
        System.out.println("""
        Selecciona el idioma del libro que deseas buscar:
        1 - Español (es)
        2 - Inglés (en)
        3 - Francés (fr)
        4 - Alemán (de)
        5 - Italiano (it)
        6 - Portugués (pt)
        0 - Volver al menú
        """);

        int opcionIdioma = teclado.nextInt();
        teclado.nextLine(); // Limpiar el buffer

        String idiomaBusqueda = switch (opcionIdioma) {
            case 1 -> "es";
            case 2 -> "en";
            case 3 -> "fr";
            case 4 -> "de";
            case 5 -> "it";
            case 6 -> "pt";
            case 0 -> {
                System.out.println("🔙 Volviendo al menú...");
                yield null;
            }
            default -> {
                System.out.println("⚠️ Opción inválida, volviendo al menú...");
                yield null;
            }
        };

        if (idiomaBusqueda != null) {
            List<Libros> librosPorIdioma = libroService.obtenerLibrosPorIdioma(idiomaBusqueda);

            if (librosPorIdioma.isEmpty()) {
                System.out.println("❌ No se encontraron libros en el idioma: " + idiomaBusqueda);
            } else {
                System.out.println("📚 Libros encontrados en " + idiomaBusqueda + ":");
                librosPorIdioma.forEach(libro -> System.out.println("🔹 " + libro.getTitulo()));
            }

            // 📊 Mostrar estadísticas corregidas
            Map<String, Long> estadisticas = libroService.obtenerEstadisticasPorIdioma();
            System.out.println("\n📊 Estadísticas de libros por idioma:");
            estadisticas.forEach((idioma, cantidad) ->
                    System.out.println("🌍 Idioma: " + idioma + " - Libros disponibles: " + cantidad));
        }
    }
}