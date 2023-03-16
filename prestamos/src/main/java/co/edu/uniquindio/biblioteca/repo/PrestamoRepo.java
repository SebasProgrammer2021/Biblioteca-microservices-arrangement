package co.edu.uniquindio.biblioteca.repo;

import co.edu.uniquindio.biblioteca.dto.PrestamoQueryDTO;
import co.edu.uniquindio.biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepo extends JpaRepository<Prestamo, Long> {

    @Query("select new co.edu.uniquindio.biblioteca.dto.PrestamoQueryDTO(p.codigo, p.codigoCliente, l, p.fechaPrestamo, p.fechaDevolucion) from Prestamo p join p.isbnLibros l where p.codigoCliente = :codigoCliente")
    List<PrestamoQueryDTO> findByCodigoCliente(String codigoCliente);

    @Query("select p from Prestamo p where date(p.fechaPrestamo) = :fecha")
    List<Prestamo> findByDate(LocalDate fecha);

    @Query("select count(p) from Prestamo p join p.isbnLibros l where l = :isbn")
    Long lendingCount(String isbn);
}
