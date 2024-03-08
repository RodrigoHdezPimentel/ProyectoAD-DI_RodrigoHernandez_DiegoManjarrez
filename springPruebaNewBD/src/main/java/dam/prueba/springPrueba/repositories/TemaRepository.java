package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Integer> {

}
