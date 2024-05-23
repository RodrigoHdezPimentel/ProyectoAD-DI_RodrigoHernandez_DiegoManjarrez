package dam.prueba.spring_boot_foroex.repositories;

import dam.prueba.spring_boot_foroex.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
    @Query(value = "SELECT c.name, c.genero FROM Usuario c WHERE c.name = ?1")
     List<String> getByNombre (String nombre);

    @Query(value = "SELECT p FROM Publicacion p WHERE p.idpublirefer is null")
     List<Publicacion> getUserPublications ();

    @Query(value = "SELECT p FROM Publicacion p WHERE p.idpublirefer = ?1")
     List<Publicacion> getAllComentsFromPublish (Integer id);

//    SELECT * FROM publicaciones WHERE fecha BETWEEN DATE_SUB(CURDATE(),
//    INTERVAL 1 YEAR) AND CURDATE() and idPubliRefer is null ORDER BY numLikes DESC;

    @Query(value = "SELECT * FROM Publicaciones p WHERE p.fecha BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 YEAR) AND CURDATE() AND p.idpublirefer IS NULL ORDER BY p.numlikes DESC", nativeQuery = true)
    List<Publicacion> getPublishTrending();
    //HAcer una query que muestres las tendencias de las publicaciones

}
