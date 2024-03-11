package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.GrupoUsuarioFK;
import dam.prueba.springPrueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, GrupoUsuarioFK> {

    //lista los grupos al que pertenece el usuario
    @Query(value = "SELECT g FROM GrupoUsuario g " +
            "WHERE g.id.idusuario = ?1")
    List<GrupoUsuario> getUserGroups (Integer id);

    //solo optiene una datos en especifico
    @Query(value = "SELECT g FROM GrupoUsuario g " +
            "WHERE g.idgrupousuario = ?1")
    GrupoUsuario getById(Integer id);

    @Query(value = "SELECT u FROM GrupoUsuario g JOIN Usuario u on u.idusuario = g.id.idusuario " +
            "WHERE g.id.idgrupo = ?1")
    List<Usuario> getGroupUsers(Integer id);


}
