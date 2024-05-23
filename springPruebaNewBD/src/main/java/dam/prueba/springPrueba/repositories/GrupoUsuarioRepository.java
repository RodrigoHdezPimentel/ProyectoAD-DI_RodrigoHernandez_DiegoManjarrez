package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.Class.ChatListUser;
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
//SELECT COUNT(c.idConversacion), g.idGrupo from  grupo_usuario g
//LEFT JOIN conversaciones c ON g.idGrupoUsuario = c.idGrupoUsuario
//WHERE  g.idGrupo in (SELECT idGrupo from grupo_usuario WHERE idUsuario = 1)
//GROUP BY g.idGrupo HAVING COUNT(c.idConversacion)=0;


//  lista los grupos al que pertenece el usuario
// JPQl no tiene soporte para usar el UNION >:(
    @Query(value = "SELECT  new dam.prueba.springPrueba.Class.ChatListUser(g,c) FROM Conversacion c JOIN GrupoUsuario g ON c.idgrupousuario = g.idgrupousuario" +
            " WHERE c.fecha IN (SELECT MAX(c.fecha) FROM GrupoUsuario g JOIN Conversacion c ON c.idgrupousuario = g.idgrupousuario" +
            " WHERE g.id.idgrupo IN (SELECT g.id.idgrupo FROM GrupoUsuario g WHERE g.id.idusuario = ?1)" +
            " GROUP BY g.id.idgrupo) AND g.id.idgrupo IN (SELECT g.id.idgrupo FROM GrupoUsuario g WHERE g.id.idusuario = ?1) " +
            " GROUP BY g.id.idgrupo ORDER BY c.fecha DESC")
   List<ChatListUser> getListChatFromUser (Integer id);
    //UNION
   @Query(value = "SELECT new dam.prueba.springPrueba.Class.ChatListUser(g,c) FROM GrupoUsuario g LEFT JOIN Conversacion c" +
           " ON c.idgrupousuario = g.idgrupousuario WHERE g.id.idgrupo IN (SELECT g.id.idgrupo from GrupoUsuario g WHERE " +
           " g.id.idusuario = ?1) GROUP BY g.id.idgrupo HAVING COUNT(c.idconversacion)=0")
   List<ChatListUser> getListChatUserWhitoutMessage(Integer id);

   //Para Complementar el metodo de listar los chats del user
   @Query(value = "SELECT g FROM GrupoUsuario g " +
           "WHERE g.id.idusuario = ?1 AND g.id.idgrupo = ?2 ")
   GrupoUsuario asignarUserChat(Integer idU, Integer idG);

   //Colocar la foto de perfil al chat (1 a 1) del otro usuario
    @Query(value = "SELECT u.foto FROM Usuario u JOIN GrupoUsuario gr ON gr.id.idusuario=u.idusuario JOIN Grupo g ON g.idgrupo = gr.id.idgrupo" +
            " WHERE u.idusuario != ?1 and g.codigo is null and g.idgrupo = ?2")
       String pathFotoUserChat(Integer idU, Integer idG);

//    SELECT u.foto FROM grupo_usuario gr JOIN grupos g ON  g.idGrupo = gr.idGrupo JOIN usuarios
//    u ON gr.idUsuario=u.idUsuario WHERE g.idGrupo = 301 AND gr.idUsuario != 1 and g.codigo is null ;

   //CONSULTA PARA SACAR EL NUMERO DE MENSAJES NO LEIDOS POR EL USER EN EL CHAT
//   SELECT COUNT(*) FROM conversaciones c JOIN grupo_usuario g ON g.idGrupoUsuario = c.idGrupoUsuario WHERE g.idGrupo = 24
//   AND c.idLeido  NOT LIKE "1,%" AND c.idLeido NOT LIKE "%,1,%" AND c.idLeido NOT LIKE "%,1";
   @Query(value = "SELECT COUNT(c.idconversacion) FROM Conversacion c JOIN GrupoUsuario g ON g.idgrupousuario = c.idgrupousuario" +
           " WHERE g.id.idgrupo = ?1" +
           " AND (c.idleido NOT LIKE '%,' || ?2 || ',%' OR c.idleido IS NULL)" +
           " AND (c.idleido NOT LIKE '%,' || ?2 OR c.idleido IS NULL)" +
           " AND (c.idleido NOT LIKE ?2 OR c.idleido IS NULL)")
      Integer numMessageNews(Integer idG, Integer idU);


   //solo optiene una datos en especifico
   @Query(value = "SELECT g FROM GrupoUsuario g " +
           "WHERE g.idgrupousuario = ?1")
   GrupoUsuario getById(Integer id);

   @Query(value = "SELECT u FROM GrupoUsuario g JOIN Usuario u on u.idusuario = g.id.idusuario " +
           "WHERE g.id.idgrupo = ?1")
   List<Usuario> getGroupUsers(Integer id);


// CONSULTA PARA SACAR AL CHAT EN COMUN QUE TIENEN AMBOS (CODIGO ES NULL)
//   SELECT g1.idGrupo FROM grupo_usuario g1 INNER JOIN grupo_usuario g2 ON g1.idGrupo = g2.idGrupo
//   JOIN grupos g ON g.idGrupo = g1.idGrupo WHERE g1.idUsuario = 1 AND g2.idUsuario =  AND g.codigo is null;

   @Query(value = "SELECT g1.id.idgrupo, g1.idgrupousuario FROM GrupoUsuario g1 JOIN GrupoUsuario g2 ON g1.id.idgrupo = g2.id.idgrupo" +
           " JOIN Grupo g ON g1.id.idgrupo = g.idgrupo" +
           " WHERE g1.id.idusuario = ?1 AND g2.id.idusuario = ?2 AND g.codigo IS NULL ")
  List<List<Integer>> getLoadChat(Integer idU, Integer idV);

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

   @Transactional
   @Modifying
   @Query(value = "UPDATE GrupoUsuario g SET g.fechabaja = null WHERE g.idgrupousuario = ?1")
   void rejoinChat(Integer idGrupoUsuario);

   @Transactional
   @Modifying
   @Query(value = "UPDATE GrupoUsuario g SET g.fechabaja = ?2 WHERE g.idgrupousuario = ?1")
   void salirGrupo(Integer idGrupoUsuario, String fecha);
}
