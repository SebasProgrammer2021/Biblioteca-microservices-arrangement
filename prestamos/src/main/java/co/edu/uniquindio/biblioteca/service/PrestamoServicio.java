package co.edu.uniquindio.biblioteca.service;

import co.edu.uniquindio.biblioteca.dto.ClienteGetDTO;
import co.edu.uniquindio.biblioteca.dto.PrestamoPostDTO;
import co.edu.uniquindio.biblioteca.dto.PrestamoQueryDTO;
import co.edu.uniquindio.biblioteca.dto.Respuesta;
import co.edu.uniquindio.biblioteca.model.Prestamo;
import co.edu.uniquindio.biblioteca.repo.PrestamoRepo;
import co.edu.uniquindio.biblioteca.service.excepciones.PrestamoNoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PrestamoServicio {

    private final RestTemplate restTemplate;

    private final PrestamoRepo prestamoRepo;
    public long save(PrestamoPostDTO prestamoDTO){

        ClienteGetDTO cliente = findClienteByCodigo(prestamoDTO.clienteID());
        log.info("cliente {}", cliente);

        Prestamo prestamo = new Prestamo();
        prestamo.setCodigoCliente(cliente.codigo());
        prestamo.setFechaPrestamo(LocalDateTime.now());

        /*TODO crear una validación para comprobar que los ISBN de los libros sí existen */

        prestamo.setIsbnLibros(prestamo.getIsbnLibros());
        prestamo.setFechaDevolucion(prestamoDTO.fechaDevolucion());

        return prestamoRepo.save(prestamo).getCodigo();
    }

    public List<Prestamo> findByCodigoCliente(String codigoCliente){

        ClienteGetDTO cliente = findClienteByCodigo(codigoCliente);
        log.info("cliente {}", cliente);

        List<PrestamoQueryDTO> lista = prestamoRepo.findByCodigoCliente(codigoCliente);
        List<Prestamo> respuesta = new ArrayList<>();

        for(PrestamoQueryDTO q : lista){
            if(respuesta.stream().noneMatch(r -> r.getCodigo() == q.getPrestamoID())){
                ArrayList<String> libros = new ArrayList<>();
                libros.add(q.getIsbnLibro());
                respuesta.add( new Prestamo(q.getPrestamoID(), q.getClienteID(), q.getFechaCreacion(), q.getFechaDevolucion(), libros ) );
            }else{
                respuesta.stream().findAny().get().getIsbnLibros().add( q.getIsbnLibro() );
            }
        }

        return new ArrayList<>();

    }

    public List<Prestamo> findAll(){
        return prestamoRepo.findAll();
    }

    public Prestamo findById(long codigoPrestamo){
        return prestamoRepo.findById(codigoPrestamo).orElseThrow(()-> new PrestamoNoEncontradoException("No existe un préstamo con el código: "+codigoPrestamo));
    }

    public List<Prestamo> findByDate(LocalDate codigoPrestamo){
        return prestamoRepo.findByDate(codigoPrestamo);
    }

    public long lendingCount(String isbn){
        return prestamoRepo.lendingCount(isbn);
    }

    public long update(long codigoPrestamo, PrestamoPostDTO prestamoPostDTO){
        Prestamo prestamo = prestamoRepo.findById(codigoPrestamo).orElseThrow(()-> new PrestamoNoEncontradoException("No existe un préstamo con el código: "+codigoPrestamo));

        /*TODO crear una validación para comprobar que los ISBN de los libros sí existen */

        prestamo.setIsbnLibros(prestamoPostDTO.isbnLibros());
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaDevolucion(prestamoPostDTO.fechaDevolucion());

        return prestamo.getCodigo();
    }

    private ClienteGetDTO findClienteByCodigo(String codigoCliente){
        try {

            Respuesta<ClienteGetDTO> respuesta = restTemplate.exchange(
                    "http://localhost:8081/api/cliente/" + codigoCliente,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Respuesta<ClienteGetDTO>>() {}).getBody();

            return respuesta.getDato();

        }catch (Exception e){
            throw new RuntimeException("Hubo un error recuperando la información del cliente");
        }
    }

}
