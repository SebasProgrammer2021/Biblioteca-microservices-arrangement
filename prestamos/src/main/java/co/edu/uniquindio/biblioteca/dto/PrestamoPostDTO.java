package co.edu.uniquindio.biblioteca.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PrestamoPostDTO(Long prestamoId, String clienteID, List<String> isbnLibros, LocalDateTime fechaDevolucion) {
}
