package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.Class.Message;
import dam.prueba.springPrueba.models.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Integer> {
    //Sacar las conversaciobes por id del grupo
    @Query(value = "SELECT new dam.prueba.springPrueba.Class.Message(c,u.idusuario,u.name) FROM Conversacion c JOIN GrupoUsuario gu " +
            "on gu.idgrupousuario = c.idgrupousuario JOIN Usuario u ON gu.id.idusuario = u.idusuario " +
            "WHERE gu.id.idgrupo = ?1 " +
            "ORDER BY c.fecha ASC")
    ArrayList<Message> getConversacionesByGroupId (Integer id);


    //Sacar el ultimo mensaje y hacer la comprobacion si es un mensaje nuevo en la App
    @Query(value = "SELECT new dam.prueba.springPrueba.Class.Message(c,u.idusuario,u.name) FROM Conversacion c JOIN GrupoUsuario g " +
            " ON c.idgrupousuario = g.idgrupousuario" +
            " JOIN Usuario u ON g.id.idusuario = u.idusuario WHERE g.id.idgrupo = ?1" +
            " ORDER BY c.fecha DESC LIMIT 1 ")
    Message getLastMessage(Integer id);
//SELECT c.*,u.idUsuario,u.Us_Nombre FROM conversaciones c JOIN grupo_usuario gu on gu.idGrupoUsuario = c.idGrupoUsuario
// JOIN usuarios u ON gu.idUsuario = u.idUsuario WHERE gu.idGrupo = 20 ORDER BY c.fecha DESC LIMIT 1;


    //UPDATE DE LAS MENSAJES
// UPDATE conversaciones c JOIN grupo_usuario g ON g.idGrupoUsuario = c.idGrupoUsuario set c.idLeido =
//    CONCAT((SELECT idLeido FROM conversaciones c JOIN grupo_usuario g ON g.idGrupoUsuario = c.idGrupoUsuario
//    WHERE g.idGrupo = 101 ORDER BY c.fecha DESC LIMIT 1), ',', '2')
//    WHERE  g.idGrupo = 101 AND
//            (c.idLeido NOT LIKE CONCAT('%,', '2', ',%') OR c.idLeido IS NULL) AND
//            (c.idLeido NOT LIKE CONCAT('%,', '2') OR c.idLeido IS NULL) AND
//            (c.idLeido NOT LIKE '2' OR c.idLeido IS NULL);

    @Transactional
    @Modifying//Cambiar idLeido de la conversacion
    @Query(value = "UPDATE Conversacion c " +
            " SET c.idleido = CONCAT((SELECT c.idleido FROM Conversacion c JOIN GrupoUsuario g ON g.idgrupousuario = c.idgrupousuario" +
            " WHERE g.id.idgrupo = ?2 ORDER BY c.fecha DESC LIMIT 1), ',', ?1)" +
            " WHERE c.idgrupousuario IN (SELECT g.idgrupousuario FROM Conversacion c JOIN GrupoUsuario g ON g.idgrupousuario = c.idgrupousuario " +
            " WHERE g.id.idgrupo = ?2)" +
            " AND (c.idleido NOT LIKE '%,' || ?1 || ',%' OR c.idleido IS NULL)" +
            " AND (c.idleido NOT LIKE '%,' || ?1 OR c.idleido IS NULL)" +
            " AND (c.idleido NOT LIKE ?1 OR c.idleido IS NULL) ")
    void readMessages(Integer idUsuario, Integer idGrupo);

    @Transactional
    @Modifying//Cambiar idLeido de la conversacion
    @Query(value = "UPDATE Conversacion c SET contenido = ?2 WHERE idconversacion = ?1")
    void updateContent(Integer idConv, String contenido);


}
