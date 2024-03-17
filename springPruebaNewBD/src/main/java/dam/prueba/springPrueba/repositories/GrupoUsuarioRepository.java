package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.Class.ChatLastMessage;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.GrupoUsuarioFK;
import dam.prueba.springPrueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, GrupoUsuarioFK> {
//Select c.*, g.* FROM conversaciones c JOIN grupo_usuario g ON c.idGrupoUsuario = g.idGrupoUsuario
// WHERE c.fecha in (SELECT MAX(c.fecha) FROM grupo_usuario g JOIN conversaciones c
// ON  c.idGrupoUsuario = g.idGrupoUsuario WHERE g.idGrupo in
// (SELECT g.idGrupo FROM grupo_usuario g WHERE g.idUsuario = 1) GROUP BY g.idGrupo)
// AND g.idGrupo IN (SELECT g.idGrupo FROM grupo_usuario g WHERE g.idUsuario = 1)
//  UNION
//SELECT c.contenido, c.fecha, g.* FROM grupo_usuario g LEFT JOIN conversaciones c
// ON g.idGrupoUsuario = c.idGrupoUsuario WHERE c.idGrupoUsuario IS NULL;


//  lista los grupos al que pertenece el usuario
// JPQl no tiene soporte para usar el UNION >:(
    @Query(value = "SELECT  new dam.prueba.springPrueba.Class.ChatLastMessage(g,c) FROM Conversacion c JOIN GrupoUsuario g ON c.idgrupousuario = g.idgrupousuario" +
            " WHERE c.fecha IN (SELECT MAX(c.fecha) FROM GrupoUsuario g JOIN Conversacion c ON c.idgrupousuario = g.idgrupousuario" +
            " WHERE g.id.idgrupo IN (SELECT g.id.idgrupo FROM GrupoUsuario g WHERE g.id.idusuario = ?1)" +
            " GROUP BY g.id.idgrupo) AND g.id.idgrupo IN (SELECT g.id.idgrupo FROM GrupoUsuario g WHERE g.id.idusuario = ?1) " +
            " ORDER BY c.fecha DESC")
   List<ChatLastMessage> getListChatFromUser (Integer id);
    //UNION
   @Query(value = "SELECT new dam.prueba.springPrueba.Class.ChatLastMessage(g,c) FROM GrupoUsuario g LEFT JOIN Conversacion c" +
           " ON c.idgrupousuario = g.idgrupousuario WHERE c.idgrupousuario IS NULL AND g.id.idusuario = ?1")
   List<ChatLastMessage> getListChatUserWhitoutMessage (Integer id);

    //Para Complementar el metodo de listar los chats del user
 @Query(value = "SELECT g FROM GrupoUsuario g " +
         "WHERE g.id.idusuario = ?1 AND g.id.idgrupo = ?2 ")
    GrupoUsuario asignarUserChat(Integer idU, Integer idG);

    //solo optiene una datos en especifico
    @Query(value = "SELECT g FROM GrupoUsuario g " +
            "WHERE g.idgrupousuario = ?1")
    GrupoUsuario getById(Integer id);

    @Query(value = "SELECT u FROM GrupoUsuario g JOIN Usuario u on u.idusuario = g.id.idusuario " +
            "WHERE g.id.idgrupo = ?1")
    List<Usuario> getGroupUsers(Integer id);

    //PARA SACAR LOS GRUPOS EN COMÚN, SACO SOLAMENT EL idGrupo
    @Query(value = "SELECT g1.id.idgrupo, g1.idgrupousuario FROM GrupoUsuario g1 JOIN Grupo g ON g1.id.idgrupo = g.idgrupo JOIN GrupoUsuario g2 " +
            "ON g2.id.idgrupo = g.idgrupo WHERE g1.id.idusuario = ?1 AND g2.id.idusuario = ?2 GROUP BY g.idgrupo")
    List<List<Integer>> getCommonGroups(Integer idU, Integer idV);
    //Para contar el numero de miembros dentro de un grupo (idGrupo)
    @Query(value = "SELECT COUNT(DISTINCT u) FROM GrupoUsuario g JOIN Usuario u on u.idusuario = g.id.idusuario " +
            "WHERE g.id.idgrupo = ?1")
    Integer getNumberUsers(Integer id);
    @Query(value = "SELECT g.nombre from GrupoUsuario g where g.id.idgrupo = ?1 and g.id.idusuario != ?2")
    List<String> getGroupName(Integer idGr, Integer idUs);

    @Transactional
    @Modifying//Cambiar nombre del grupo
    @Query(value = "UPDATE GrupoUsuario g SET g.nombre = ?1 WHERE g.idgrupousuario = ?2")
    void updateChatName(String newName, Integer id);
    //Cambiar nombre del chat(sin cambiar el de la persona)
    @Transactional
    @Modifying
    @Query(value = "UPDATE GrupoUsuario g SET g.nombre = ?1 WHERE g.id.idgrupo = " +
            "           (SELECT gu.id.idgrupo from GrupoUsuario gu WHERE gu.idgrupousuario = ?2)")
    void updateGroupName(String newName, Integer id);

}
