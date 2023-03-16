package co.edu.uniquindio.biblioteca.servicio.excepciones;

import co.edu.uniquindio.biblioteca.dto.Respuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Respuesta<String>> handlerGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Respuesta<>(e.getMessage() + " ----- dateTime:" + LocalDateTime.now()));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Respuesta<String>> handlerCustomerNotFoundException(CustomerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Respuesta<>(e.getMessage()));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Respuesta<String>> handlerBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Respuesta<>(e.getMessage()));
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<Respuesta<String>> handlerAuthorNotFoundException(AuthorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Respuesta<>(e.getMessage()));
    }
}
