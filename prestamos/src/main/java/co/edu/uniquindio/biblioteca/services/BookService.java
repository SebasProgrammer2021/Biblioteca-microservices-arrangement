package co.edu.uniquindio.biblioteca.services;

import co.edu.uniquindio.biblioteca.dto.LibroIsbnDTO;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private final RestTemplate restTemplate;

    public String findLibrosByIbsn(List<String> ibsns) {
        try {
            HttpEntity<List<String>> entity = new HttpEntity<>(ibsns);

            Respuesta<LibroIsbnDTO> respuesta = restTemplate.exchange(
                    "http://localhost:8081/api/libro/validateBookList",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Respuesta<LibroIsbnDTO>>() {
                    }).getBody();

            LibroIsbnDTO result = respuesta != null ? respuesta.getDato() : null;
            String solucion = "";
            if (!result.noEncontrados().isEmpty()) {
                solucion = result.noEncontrados().stream().collect(Collectors.joining());
                solucion = "Los ibsn no econtrados son: " + solucion;
            }
            return solucion;

        } catch (Exception e) {
            throw new RuntimeException("Hubo un error recuperando la información del cliente");
        }

    }
}
