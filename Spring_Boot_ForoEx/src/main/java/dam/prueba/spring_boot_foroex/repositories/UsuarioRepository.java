package dam.prueba.spring_boot_foroex.repositories;

import dam.prueba.spring_boot_foroex.models.Publicacion;
import dam.prueba.spring_boot_foroex.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT c.name, c.genero FROM Usuario c WHERE c.name = ?1")
     List<String> getByNombre (String nombre);

    @Query("SELECT p FROM Publicacion p WHERE p.idusuario = ?1 and idpublirefer is null ORDER BY p.idpublicacion DESC")
    List<Publicacion> getUserPublicacion(Integer userId);


    @Query("SELECT DISTINCT p FROM Publicacion p INNER JOIN Usuario u ON p.idusuario = u.idusuario" +
                " INNER JOIN UsuarioTema ut ON ut.id.idTema = p.idtema WHERE ut.id.idUsuario = ?1 and p.idpublirefer is null ORDER BY p.numlikes DESC")
    List<Publicacion> getUserPublicacionFromTema(Integer userId);

//    SELECT p.* fROM likes l inner JOIN publicaciones p ON p.idPublicacion = l.idPublicacion where p.idPubliRefer is null AND l.idUsuario = 1
//    group by p.idPublicacion
    @Query("SElECT p FROM Like l INNER JOIN Publicacion p ON p.idpublicacion = l.idPublicacion WHERE l.idUsuario = ?1 group by p.idpublicacion" +
            " ORDER BY l.idLike DESC")
    List<Publicacion> getUserPublicacionFromLike(Integer userId);

    @Query("SElECT c FROM Usuario c WHERE c.name = ?1 AND c.pass = ?2")
    Optional<Usuario> getUserRegister(String name, String pass);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u set u.fechabaja = ?2 WHERE u.idusuario = ?1")
    void deleteUsuario(Integer id, String fecha);

    //-------FALTA COLOCAR LOS UPDATE CUANDO SE ACTUALIZA EL USER EN EL EDITPROFILE--//



    //---------------------colocar las fotos a cada user------------------
    @Transactional
    @Modifying
    @Query(value = "UPDATE Usuario u SET u.foto = ?2 WHERE u.idusuario = ?1")
    void fotoUsuario(Integer idU, String image);
    //--------------------------------------------------------------------
}
