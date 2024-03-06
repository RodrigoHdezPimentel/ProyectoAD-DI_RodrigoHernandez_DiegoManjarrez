package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Chat;
import dam.prueba.springPrueba.models.Usuario;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    //    @Query("SELECT DISTINCT c " +
//            "FROM Chat c WHERE c.idOrigen = ?1 " +
//            "UNION " +
//            "SELECT DISTINCT c " +
//            "FROM Chat c WHERE c.idDestino = ?1")
@Query("SELECT c " +
        "FROM Chat c WHERE c.idOrigen = ?1 GROUP BY c.idDestino " +
        "UNION " +
        "SELECT c " +
        "FROM Chat c WHERE c.idDestino = ?1 GROUP BY c.idOrigen")
    List<Chat> getUserChats (Integer id);

//    SELECT * FROM `chat` WHERE idUsuarioOrigen = 1 and idUsuarioDestino = 48 and
//            fecha = (SELECT max(fecha) from chat WHERE idUsuarioOrigen = 1 and idUsuarioDestino = 48)
    
//    @Query("SELECT c " +
//        "FROM Chat c WHERE c.idOrigen = ?1 and c.fecha = (SELECT max(fecha)) FROM Chat WHERE idOrigen = ?1)" )
//        Chat getUserChatsFilter (Integer id);
    @Query("SELECT c FROM Chat c Where c.idDestino = ?1 or idOrigen = ?1")
    List<Chat> getUsersConversation(Integer id1);


}
