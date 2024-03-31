package dam.prueba.springPrueba.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private Integer idusuario;

    @Column(name = "anionacimiento")
    private Integer year;

    @Column(name = "Us_Nombre")
    private String name;

    @Column(name = "Us_Genero")
    private Boolean genero;

    @Column(name = "Us_Descripcion")
    private String descripcion;

    @Column(name = "Us_Mail")
    private String mail;

    @Column(name = "Us_Contrasena")
    private String pass;

    @Column(name = "foto")
    private String foto;

    @Column(name = "fechabaja")
    private String fechabaja;
}
