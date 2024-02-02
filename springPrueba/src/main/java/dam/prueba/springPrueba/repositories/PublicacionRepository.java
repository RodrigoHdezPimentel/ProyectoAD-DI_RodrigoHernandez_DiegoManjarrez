package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {

}
