package co.edu.uniquindio.biblioteca.servicio.excepciones;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String mensaje){
        super(mensaje);
    }

}
