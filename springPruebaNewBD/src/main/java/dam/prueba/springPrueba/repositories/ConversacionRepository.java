package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Integer> {
    @Query(value = "SELECT c FROM Conversacion c  " +
            "WHERE c.grupousuario.id.grupo.idgrupo = ?1 " +
            "ORDER BY fecha DESC")
    List<Conversacion> getConversacionesByGroupId (Integer id);

}
