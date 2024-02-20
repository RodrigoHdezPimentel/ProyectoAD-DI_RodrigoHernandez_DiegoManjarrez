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


    @Query("SELECT DISTINCT p FROM Publicacion p INNER JOIN Usuario u ON p.idusuario = u.idusuario" +
                " INNER JOIN UsuarioTema ut ON ut.id.idTema = p.idtema WHERE ut.id.idUsuario = ?1 and p.idpublirefer is null ORDER BY p.numlikes DESC")
    List<Publicacion> getUserPublicacionFromTema(Integer userId);

//    SELECT p.* fROM likes l inner JOIN publicaciones p ON p.idPublicacion = l.idPublicacion where p.idPubliRefer is null AND l.idUsuario = 1
//    group by p.idPublicacion

    @Query("SElECT p FROM Like l INNER JOIN Publicacion p ON p.idpublicacion = l.idPublicacion WHERE p.idpublirefer is null and l.idUsuario = ?1 group by p.idpublicacion")
    List<Publicacion> getUserPublicacionFromLike(Integer userId);



}
