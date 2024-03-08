package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Integer> {

}
