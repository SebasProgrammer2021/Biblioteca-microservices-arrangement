package co.edu.uniquindio.biblioteca.dto;

import co.edu.uniquindio.biblioteca.enums.Gender;

import java.time.LocalDate;
import java.util.List;

public record LibroDTO(String isbn, String nombre, Gender genero, int unidades, LocalDate fechaPublicacion, List<Long> idAutores) {
}
