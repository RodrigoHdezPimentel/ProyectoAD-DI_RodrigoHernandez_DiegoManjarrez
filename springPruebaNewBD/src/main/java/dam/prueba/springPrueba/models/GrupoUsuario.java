package dam.prueba.springPrueba.models;

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

    private GrupoUsuarioFK id;
}
