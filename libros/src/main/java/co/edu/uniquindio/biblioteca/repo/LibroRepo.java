package co.edu.uniquindio.biblioteca.repo;

import co.edu.uniquindio.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepo extends JpaRepository<Libro, String> {
}
