package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Integer> {
    //Sacar las conversaciobes poor id del grupo
    @Query(value = "SELECT c FROM Conversacion c JOIN GrupoUsuario gu " +
            "on gu.idgrupousuario = c.idgrupousuario " +
            "WHERE gu.id.idgrupo = ?1 " +
            "ORDER BY c.fecha ASC")
    List<Conversacion> getConversacionesByGroupId (Integer id);


    //Sacar el ultimo mensaje y la fecha
    @Query(value = "SELECT c FROM Conversacion c INNER JOIN GrupoUsuario g " +
            "ON c.idgrupousuario = g.idgrupousuario WHERE g.id.idgrupo = ?1 AND " +
            "c.fecha = (SELECT MAX (c.fecha) FROM Conversacion c INNER JOIN GrupoUsuario g " +
            "ON c.idgrupousuario = g.idgrupousuario WHERE g.id.idgrupo = ?1 ) ")
    Conversacion getLastMessage(Integer id);
//SELECT c.* FROM conversaciones c INNER JOIN grupo_usuario g ON c.idGrupoUsuario = g.idGrupoUsuario  WHERE g.idGrupo = 2 AND
//c.fecha = (SELECT MAx(c.fecha) FROM conversaciones c INNER JOIN grupo_usuario g ON c.idGrupoUsuario = g.idGrupoUsuario  WHERE g.idGrupo = 2 );
    @Transactional
    @Modifying//Cambiar idLeido de la conversacion
    @Query(value = "UPDATE Conversacion c " +
            "           SET c.idleido = CONCAT(" +
            "               (SELECT c1.idleido from Conversacion c1 where c1.idconversacion = ?2), ',', ?1) WHERE c.idconversacion = ?2")
    void readMessage(Integer idUsuario, Integer idConversacion);
}
