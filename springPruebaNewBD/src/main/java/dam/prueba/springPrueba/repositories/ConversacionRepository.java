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


    //Sacar el ultimo mensaje y hacer la comprobacion si es un mensaje nuevo en la App
    @Query(value = "SELECT new dam.prueba.springPrueba.Class.LoadConversation(c,u.idusuario,u.name) FROM Conversacion c JOIN GrupoUsuario g " +
            " ON c.idgrupousuario = g.idgrupousuario" +
            " JOIN Usuario u ON g.id.idusuario = u.idusuario WHERE g.id.idgrupo = ?1" +
            " ORDER BY c.fecha DESC LIMIT 1 ")
    LoadConversation getLastMessage(Integer id);
//SELECT c.*,u.idUsuario,u.Us_Nombre FROM conversaciones c JOIN grupo_usuario gu on gu.idGrupoUsuario = c.idGrupoUsuario
// JOIN usuarios u ON gu.idUsuario = u.idUsuario WHERE gu.idGrupo = 20 ORDER BY c.fecha DESC LIMIT 1;
}
