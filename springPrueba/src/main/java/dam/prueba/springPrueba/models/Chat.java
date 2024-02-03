package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Chat") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Chat {
    @Id
    @Column(name = "idusuariodestino")
    private Integer idDestino;
    @Column(name = "idusuarioorigen")
    private Integer idOrigen;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "contenido")
    private String contenido ;
}
