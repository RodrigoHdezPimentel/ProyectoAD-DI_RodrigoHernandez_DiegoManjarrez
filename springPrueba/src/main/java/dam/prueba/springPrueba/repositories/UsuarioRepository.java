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

    @Query("SELECT p FROM Publicacion p WHERE p.idusuario = ?1 and idpublirefer is null")
    List<Publicacion> getUserPublicacion(Integer userId);


//    @Query("SELECT p FROM Publicacion p JOIN p.Usuario u JOIN u.UsuarioTema ut WHERE ut.idUsuario = ?1")
//    List<Publicacion> getUserPublicacion(Integer userId);

//    SELECT DISTINCT p.id, p.idusuario, p.idtema, p.idpublirefer, p.fecha, p.numlikes, p.contenido, p.titulo " +
//            " FROM Publicacion p INNER JOIN  Usuario u ON u.id =" +
//            "    p.id INNER JOIN UsuarioTemaFK ut ON ut.idTema = p.idtema WHERE p.idpublirefer IS NULL and u.id = ?1
    @Query("SELECT DISTINCT p FROM Publicacion p INNER JOIN Usuario u ON p.idusuario = u.id" +
                " INNER JOIN UsuarioTema ut ON ut.id.idTema = p.idtema WHERE ut.id.idUsuario = ?1 and p.idpublirefer is null ORDER BY p.numlikes DESC")
    List<Publicacion> getUserPublicacionFromTema(Integer userId);

}
