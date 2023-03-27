package co.edu.uniquindio.biblioteca.servicio;

import co.edu.uniquindio.biblioteca.dto.LibroDTO;
import co.edu.uniquindio.biblioteca.dto.LibroIsbnDTO;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
import co.edu.uniquindio.biblioteca.model.Autor;
import co.edu.uniquindio.biblioteca.model.Libro;
import co.edu.uniquindio.biblioteca.repo.AutorRepo;
import co.edu.uniquindio.biblioteca.repo.LibroRepo;
import co.edu.uniquindio.biblioteca.servicio.excepciones.AuthorNotFoundException;
import co.edu.uniquindio.biblioteca.servicio.excepciones.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibroServicio {
    private final LibroRepo libroRepo;
    private final AutorRepo autorRepo;

    public Libro save(LibroDTO libro) {


        Optional<Libro> guardado = libroRepo.findById(libro.isbn());

        if (guardado.isPresent()) {
            throw new RuntimeException("El libro con el isbn " + libro.isbn() + " ya existe.");
        }

        return libroRepo.save(convertir(libro));
    }

    public void delete(String bookId) {
        libroRepo.deleteById(bookId);
    }

    public Libro findById(String isbn) {
        return libroRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException("El libro no existe"));
    }

    public List<Libro> findAll() {
        return libroRepo.findAll();
    }

    public Libro update(LibroDTO libro) {
        return libroRepo.save(convertir(libro));
    }

    private Libro convertir(LibroDTO libro) {

        List<Autor> existingAuthorsList = autorRepo.findAllById(libro.idAutores());

        if (existingAuthorsList.size() != libro.idAutores().size()) {
            List<Long> idsExistentes = existingAuthorsList.stream().map(Autor::getId).toList();
            String listaAutoresNoEncontrados = libro.idAutores()
                    .stream()
                    .filter(id -> !idsExistentes.contains(id))
                    .map(Object::toString)
                    .collect(Collectors.joining(","));

            throw new AuthorNotFoundException("El autor: " + listaAutoresNoEncontrados + " no existe.");
        }

        Libro nuevo = Libro.builder()
                .isbn(libro.isbn().trim())
                .nombre(libro.nombre())
                .genero(libro.genero())
                .fechaPublicacion(libro.fechaPublicacion())
                .unidades(libro.unidades())
                .autor(existingAuthorsList)
                .build();

        return nuevo;
    }

    public LibroIsbnDTO validateBookList(List<String> bookIsbList) {
        List<Libro> existingBooksList = libroRepo.findAllById(bookIsbList);
        boolean result = true;

        //result = false;
        List<String> idsExistentes = existingBooksList.stream().map(Libro::getIsbn).toList();
        List<String> noEncontrados = bookIsbList
                .stream()
                .filter(id -> !idsExistentes.contains(id))
                .map(Object::toString)
                .collect(Collectors.toList());

        LibroIsbnDTO respuesta = new LibroIsbnDTO(idsExistentes, noEncontrados);

        return respuesta;
    }
}
