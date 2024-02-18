package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT c.name, c.genero FROM Usuario c WHERE c.name = ?1")
    public List<String> getByNombre (String nombre);

    @Query("SELECT p FROM Publicacion p WHERE p.idusuario = ?1")
    List<Publicacion> getUserPublicacion(Integer userId);
//    @Query("SELECT p FROM Publicacion p JOIN p.Usuario u JOIN u.UsuarioTema ut WHERE ut.idUsuario = ?1")
//    List<Publicacion> getUserPublicacion(Integer userId);
}
