package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Conversaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversacionesRepository extends JpaRepository<Conversaciones, Integer> {
    @Query(value = "SELECT c FROM Conversaciones c WHERE c.idconversacion = ?1")
    Optional<Conversaciones> getConversacionesById (Integer id);

}
