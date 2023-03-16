package co.edu.uniquindio.biblioteca.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("cliente")
@NoArgsConstructor
@Data
public class Cliente implements Serializable {

    @Id
    private String codigo;

    private String nombre;

    private String email;

    private String telefono;

    private String password;

    @Builder
    public Cliente(String nombre, String email, String telefono, String password) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }
}
