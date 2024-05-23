package dam.prueba.spring_boot_foroex.repositories;

import dam.prueba.spring_boot_foroex.models.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Integer> {

}
