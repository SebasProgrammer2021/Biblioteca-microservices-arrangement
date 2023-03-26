package co.edu.uniquindio.biblioteca.services.utils.loan;

import co.edu.uniquindio.biblioteca.dto.ClienteGetDTO;
import co.edu.uniquindio.biblioteca.dto.PrestamoPostDTO;
import co.edu.uniquindio.biblioteca.model.Prestamo;
import co.edu.uniquindio.biblioteca.repo.PrestamoRepo;
import co.edu.uniquindio.biblioteca.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LoanUtils {
    private final CustomerService customerService;

    private final PrestamoRepo loanRepo;
//    private final CustomerRepo customerRepo;
//
//    private final BookRepo bookRepo;

    /**
     * getLoan
     *
     * @param code
     */
//    public void validateLoanExistence(Long code) {
//        loanRepo.findById(code).orElseThrow(() -> new BookNotFoundException("El Prestamo no existe."));
//    }
//
//    public Prestamo getLoan(Long code) {
//        Prestamo loanFinded = loanRepo.findById(code).orElseThrow(() -> new BookNotFoundException("El Prestamo no existe."));
//        return loanFinded;
//    }
//
//    public List<LoanGetDTO> setListLoanGetDTO(List<Prestamo> loanList) {
//        return loanList.stream().map(this::transformLoanToLoanGetDTO).toList();
//    }
//
//    public LoanGetDTO transformLoanToLoanGetDTO(Prestamo prestamo) {
//        return new LoanGetDTO(prestamo.getCodigo(), prestamo.getCliente().getCodigo(), prestamo.getLibros().stream().map(Libro::getIsbn).toList(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion());
//    }

    /**
     * transformLoanToLoanDTO
     *
     * @param loan
     * @return LoanBodyDTO
     */
    public PrestamoPostDTO transformLoanToLoanDTO(Prestamo loan) {
        return new PrestamoPostDTO(loan.getCodigo(), loan.getCodigoCliente(), loan.getIsbnLibros(), loan.getFechaDevolucion());
    }

    /**
     * validateLoan
     *
     * @param loanBodyDTO
     * @param loanId
     * @return Prestamo
     */
    public Prestamo transformPrestamoPostDtoToPrestamo(PrestamoPostDTO loanBodyDTO, long loanId, LocalDateTime fechaPrestamo) {
        ClienteGetDTO cliente = customerService.findCustomerByCode(loanBodyDTO.clienteID());

        return Prestamo.builder().codigo(loanId).codigoCliente(cliente.codigo()).fechaPrestamo(fechaPrestamo).fechaDevolucion(loanBodyDTO.fechaDevolucion()).isbnLibros(loanBodyDTO.isbnLibros()).build();
    }


}
