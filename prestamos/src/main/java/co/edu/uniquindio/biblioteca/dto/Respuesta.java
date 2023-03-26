package co.edu.uniquindio.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Respuesta<T>{

    private String message;
    private T response;

    public Respuesta(String mensaje) {
        this.message = mensaje;
    }
}
