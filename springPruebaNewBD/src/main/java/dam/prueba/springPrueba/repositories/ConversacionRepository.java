package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.Class.LoadConversation;
import dam.prueba.springPrueba.models.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Integer> {
    //Sacar las conversaciobes por id del grupo
    @Query(value = "SELECT new dam.prueba.springPrueba.Class.LoadConversation(c,u.idusuario,u.name) FROM Conversacion c JOIN GrupoUsuario gu " +
            "on gu.idgrupousuario = c.idgrupousuario JOIN Usuario u ON gu.id.idusuario = u.idusuario " +
            "WHERE gu.id.idgrupo = ?1 " +
            "ORDER BY c.fecha ASC")
    ArrayList<LoadConversation> getConversacionesByGroupId (Integer id);


    //Sacar el ultimo mensaje y la fecha
    @Query(value = "SELECT c FROM Conversacion c INNER JOIN GrupoUsuario g " +
            "ON c.idgrupousuario = g.idgrupousuario WHERE g.id.idgrupo = ?1 AND " +
            "c.fecha = (SELECT MAX (c.fecha) FROM Conversacion c INNER JOIN GrupoUsuario g " +
            "ON c.idgrupousuario = g.idgrupousuario WHERE g.id.idgrupo = ?1 ) ")
    Conversacion getLastMessage(Integer id);
//SELECT c.* FROM conversaciones c INNER JOIN grupo_usuario g ON c.idGrupoUsuario = g.idGrupoUsuario  WHERE g.idGrupo = 2 AND
//c.fecha = (SELECT MAx(c.fecha) FROM conversaciones c INNER JOIN grupo_usuario g ON c.idGrupoUsuario = g.idGrupoUsuario  WHERE g.idGrupo = 2 );

}
