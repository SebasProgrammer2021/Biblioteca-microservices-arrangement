package co.edu.uniquindio.biblioteca.services;

import co.edu.uniquindio.biblioteca.dto.ClienteGetDTO;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final RestTemplate restTemplate;

    public ClienteGetDTO findCustomerByCode(String codigoCliente) {
        try {
            Respuesta<ClienteGetDTO> respuesta = restTemplate.exchange(
                    "http://localhost:8081/api/cliente/" + codigoCliente,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Respuesta<ClienteGetDTO>>() {
                    }).getBody();
            return respuesta != null ? respuesta.getDato() : null;
        } catch (Exception e) {
            throw new RuntimeException("Hubo un error recuperando la información del cliente -- message: " + e.getMessage());
        }
    }
}
