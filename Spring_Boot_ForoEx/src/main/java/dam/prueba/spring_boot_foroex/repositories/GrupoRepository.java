package dam.prueba.spring_boot_foroex.repositories;

import dam.prueba.spring_boot_foroex.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    @Query(value = "SELECT g FROM Grupo g " +
            "WHERE g.codigo = ?1")
    Grupo findGroup(String codigo);

   // SELECT COUNT(gu.idUsuario) FROM grupos g, grupo_usuario gu
    //WHERE gu.idGrupo = g.idGrupo AND g.codigo = "prueba" AND gu.idUsuario = 1;

    // ESTA QUERY ME ASEGURA SI EL USUARIO YA ESTÁ DENTRO DEL GRUPO AL CUAL LE DIO CLICK
    //AL LINK DE LA URL(UNIRSE A UN GRUPO)
    @Query(value="SELECT COUNT(gu.id.idusuario) FROM Grupo g, GrupoUsuario gu " +
            " WHERE gu.id.idgrupo = g.idgrupo AND g.codigo = ?1 AND gu.id.idusuario = ?2")
    byte findUserInGroup (String codigo, Integer idU);

}
