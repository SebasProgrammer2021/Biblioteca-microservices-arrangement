package co.edu.uniquindio.biblioteca.model;

import co.edu.uniquindio.biblioteca.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Libro implements Serializable {

    @Id
    private String isbn;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Gender genero;

    @Column(nullable = false)
    private int unidades;

    @Column(nullable = false)
    private LocalDate fechaPublicacion;

    @ManyToMany
    private List<Autor> autor;
}
