package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.Respuesta;
import co.edu.uniquindio.biblioteca.dto.author.AuthorDTO;
import co.edu.uniquindio.biblioteca.model.Autor;
import co.edu.uniquindio.biblioteca.servicio.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/author")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Respuesta<String>> deleteAuthor(@PathVariable long authorId) {
        authorService.delete(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("El autor se elimino correctamente"));
    }

    @GetMapping
    public ResponseEntity<Respuesta<List<AuthorDTO>>> findAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Autores encontrados", authorService.findAll()));
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Respuesta<Autor>> findAuthorById(@PathVariable Long authorId) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Autor encontrado", authorService.findById(authorId)));
    }

    @PostMapping
    public ResponseEntity<Respuesta<Autor>> saveAuthor(@RequestBody Autor author) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Autor creado correctamente", authorService.save(author)));
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Respuesta<Autor>> updateAuthor(@PathVariable Long authorId, @RequestBody Autor author) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("El Autor se actualizo correctamente", authorService.update(authorId, author)));
    }
}
