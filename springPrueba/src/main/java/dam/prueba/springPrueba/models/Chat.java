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
    @Column(name = "idchat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idchat;
    @Column(name = "idusuariodestino", insertable=false, updatable=false)
    private Integer idDestino;
    @Column(name = "idusuarioorigen", insertable=false, updatable=false)
    private Integer idOrigen;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "contenido")
    private String contenido ;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "idusuariodestino")
    private Usuario usuarioDes;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "idusuarioorigen")
    private Usuario usuarioOr;
}
