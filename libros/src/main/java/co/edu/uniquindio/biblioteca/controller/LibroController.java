package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.LibroDTO;
import co.edu.uniquindio.biblioteca.dto.LibroIsbnDTO;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
import co.edu.uniquindio.biblioteca.model.Libro;
import co.edu.uniquindio.biblioteca.servicio.LibroServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libro")
@AllArgsConstructor
public class LibroController {

    private final LibroServicio libroServicio;

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Respuesta<String>> delete(@PathVariable String bookId) {
        libroServicio.delete(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Libro borrado correctamente"));
    }

    @GetMapping
    public ResponseEntity<Respuesta<List<Libro>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Libros encontrados", libroServicio.findAll()));
    }

    @GetMapping("/{isbnLibro}")
    public ResponseEntity<Respuesta<Libro>> findById(@PathVariable String isbnLibro) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("Libro encontrado", libroServicio.findById(isbnLibro)));
    }

    @PostMapping("/validateBookList")
    public ResponseEntity<Respuesta<LibroIsbnDTO>> validateBookList(@RequestBody List<String> bookIsbList) {
        return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>("", libroServicio.validateBookList(bookIsbList)));
    }

    @PostMapping
    public ResponseEntity<Respuesta<Libro>> save(@RequestBody LibroDTO libroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Respuesta<>("Libro creado correctamente", libroServicio.save(libroDTO)));
    }

    @PutMapping
    public ResponseEntity<Respuesta<Libro>> udpate(@RequestBody LibroDTO libroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Respuesta<>("Libro actualizado correctamente", libroServicio.update(libroDTO)));
    }

}
