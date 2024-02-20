package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
    @Query(value = "SELECT c.name, c.genero FROM Usuario c WHERE c.name = ?1")
    public List<String> getByNombre (String nombre);

        //HAcer una query que muestres las tendencias de las publicaciones
}
