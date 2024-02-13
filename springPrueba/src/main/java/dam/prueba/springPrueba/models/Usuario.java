package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Usuarios") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Usuario {
    @Id
    @Column(name = "idusuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "anionacimiento")
    private Integer year;
    @Column(name = "Us_Nombre")
    private String name;

    @Column(name = "Us_Genero")
    private String genero;
    @Column(name = "Us_Descripcion")
    private String descripcion;
    @Column(name = "Us_Mail")
    private String mail;
    @Column(name = "Us_Contrasena")
    private String pass;
}