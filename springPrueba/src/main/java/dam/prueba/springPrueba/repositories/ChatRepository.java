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

    //QUERY PA SACAR A LOS USUARIOS QUE HAN ABIERTO UN CHAT CON EL USUARIO REGISTRADO
    @Query("SELECT DISTINCT u " +
                "FROM Chat c join Usuario u on u.idusuario = c.idDestino WHERE c.idOrigen = ?1 " +
            "UNION " +
            "SELECT DISTINCT u " +
                "FROM Chat c join Usuario u on u.idusuario = c.idOrigen WHERE c.idDestino = ?1")
    public List<Usuario> getUserChats (Integer id);




}
