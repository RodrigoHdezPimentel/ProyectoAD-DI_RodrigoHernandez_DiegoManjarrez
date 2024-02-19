package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Chat;
import dam.prueba.springPrueba.models.Like;
import dam.prueba.springPrueba.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    /*@Query("SELECT p FROM Publicacion p JOIN p.idusuario = ?1")
    List<Publicacion> getUserPublicacion(Integer userId);*/
}
