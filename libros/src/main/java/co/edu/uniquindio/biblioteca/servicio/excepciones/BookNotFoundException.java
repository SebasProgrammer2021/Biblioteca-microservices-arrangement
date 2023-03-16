package co.edu.uniquindio.biblioteca.servicio.excepciones;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
