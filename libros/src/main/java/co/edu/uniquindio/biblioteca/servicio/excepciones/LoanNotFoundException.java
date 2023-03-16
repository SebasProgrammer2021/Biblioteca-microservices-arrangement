package co.edu.uniquindio.biblioteca.servicio.excepciones;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}
