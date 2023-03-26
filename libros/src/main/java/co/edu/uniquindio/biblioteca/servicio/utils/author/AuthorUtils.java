package co.edu.uniquindio.biblioteca.servicio.utils.author;

import co.edu.uniquindio.biblioteca.dto.author.AuthorDTO;
import co.edu.uniquindio.biblioteca.model.Autor;
import co.edu.uniquindio.biblioteca.repo.AutorRepo;
import co.edu.uniquindio.biblioteca.servicio.excepciones.AuthorNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthorUtils {
    private final AutorRepo authorRepo;

    public Autor getAuthor(long id) {
        return authorRepo.findById(id).orElseThrow(() -> new AuthorNotFoundException("El Autor no existe"));
    }

    public AuthorDTO convertAuthorToAuthorDTO(Autor author) {
        return new AuthorDTO(author.getId(), author.getNombre());
    }
}
