package dam.prueba.spring_boot_foroex.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grupo_usuario") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GrupoUsuario {
    @Id
    @Column(name = "idgrupousuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idgrupousuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fechabaja")
    private String fechabaja;

    private GrupoUsuarioFK id;


}
