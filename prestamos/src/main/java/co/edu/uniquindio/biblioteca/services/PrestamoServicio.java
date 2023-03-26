package co.edu.uniquindio.biblioteca.services;

import co.edu.uniquindio.biblioteca.dto.*;
import co.edu.uniquindio.biblioteca.model.Prestamo;
import co.edu.uniquindio.biblioteca.repo.PrestamoRepo;
import co.edu.uniquindio.biblioteca.services.excepciones.PrestamoNoEncontradoException;
import co.edu.uniquindio.biblioteca.services.utils.loan.LoanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PrestamoServicio {
    private final CustomerService customerService;
    private final BookService bookService;
    private final LoanUtils loanUtils;
    private final PrestamoRepo prestamoRepo;



    public List<Prestamo> findByCodigoCliente(String codigoCliente) {

        ClienteGetDTO cliente = customerService.findCustomerByCode(codigoCliente);
        log.info("cliente {}", cliente);

        List<PrestamoQueryDTO> lista = prestamoRepo.findByCodigoCliente(codigoCliente);
        List<Prestamo> respuesta = new ArrayList<>();

        for (PrestamoQueryDTO q : lista) {
            if (respuesta.stream().noneMatch(r -> r.getCodigo() == q.getPrestamoID())) {
                ArrayList<String> libros = new ArrayList<>();
                libros.add(q.getIsbnLibro());
                respuesta.add(new Prestamo(q.getPrestamoID(), q.getClienteID(), q.getFechaCreacion(), q.getFechaDevolucion(), libros));
            } else {
                respuesta.stream().findAny().get().getIsbnLibros().add(q.getIsbnLibro());
            }
        }

        return new ArrayList<>();

    }

    public List<Prestamo> findAll() {
        return prestamoRepo.findAll();
    }

    public Prestamo findById(long codigoPrestamo) {
        return prestamoRepo.findById(codigoPrestamo).orElseThrow(() -> new PrestamoNoEncontradoException("No existe un préstamo con el código: " + codigoPrestamo));
    }

    public List<Prestamo> findByDate(LocalDate codigoPrestamo) {
        return prestamoRepo.findByDate(codigoPrestamo);
    }

    public long lendingCount(String isbn) {
        return prestamoRepo.lendingCount(isbn);
    }

    public PrestamoPostDTO save(PrestamoPostDTO prestamoDTO) {
        ClienteGetDTO cliente = customerService.findCustomerByCode(prestamoDTO.clienteID());
        Prestamo prestamo = new Prestamo();
        prestamo.setCodigoCliente(cliente.codigo());
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaDevolucion(prestamoDTO.fechaDevolucion());
        String mensaje = bookService.findLibrosByIbsn(prestamoDTO.isbnLibros());
        System.out.println(mensaje);
        if (mensaje != "") {
            throw new RuntimeException("No se pudo registrar el prestamo porque los isbns: [" + mensaje + "] no fueron encontrados.");
        }
        prestamo.setIsbnLibros(prestamoDTO.isbnLibros());
        return loanUtils.transformLoanToLoanDTO(prestamoRepo.save(prestamo));
    }

    public PrestamoPostDTO update(long codigoPrestamo, PrestamoPostDTO prestamoPostDTO) {
        Prestamo prestamo = prestamoRepo.findById(codigoPrestamo).orElseThrow(() -> new PrestamoNoEncontradoException("No existe un préstamo con el código: " + codigoPrestamo));
        String mensaje = bookService.findLibrosByIbsn(prestamoPostDTO.isbnLibros());
        System.out.println(mensaje);
        if (mensaje != "") {
            throw new RuntimeException("No se pudo registrar el prestamo porque los isbns: [" + mensaje + "] no fueron encontrados.");
        }

        prestamo.setIsbnLibros(prestamoPostDTO.isbnLibros());
        prestamo.setFechaDevolucion(prestamoPostDTO.fechaDevolucion());

        Prestamo nuevo = loanUtils.transformPrestamoPostDtoToPrestamo(prestamoPostDTO, codigoPrestamo, prestamo.getFechaPrestamo());
        nuevo.setCodigo(codigoPrestamo);
        return loanUtils.transformLoanToLoanDTO(prestamoRepo.save(nuevo));
    }
}
