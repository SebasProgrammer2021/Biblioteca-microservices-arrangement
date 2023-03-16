package co.edu.uniquindio.biblioteca.servicio.excepciones;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
