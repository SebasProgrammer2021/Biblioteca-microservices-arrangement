package co.edu.uniquindio.biblioteca.service.excepciones;

public class PrestamoNoEncontradoException extends RuntimeException{

    public PrestamoNoEncontradoException(String mensaje){
        super(mensaje);
    }

}
