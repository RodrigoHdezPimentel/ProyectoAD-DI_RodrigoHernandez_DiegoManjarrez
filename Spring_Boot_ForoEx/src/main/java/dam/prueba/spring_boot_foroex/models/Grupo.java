package dam.prueba.spring_boot_foroex.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Grupos") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Grupo {
    @Id
    @Column(name = "idgrupo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idgrupo;

    @Column(name = "foto")
    private String foto;

    @Column(name = "codigo")
    private String codigo;
}
