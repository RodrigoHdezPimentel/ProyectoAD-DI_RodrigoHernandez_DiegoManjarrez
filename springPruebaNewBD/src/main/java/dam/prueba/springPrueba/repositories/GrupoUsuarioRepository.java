package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.GrupoUsuarioFK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, GrupoUsuarioFK> {
    @Query(value = "SELECT g FROM GrupoUsuario g " +
            "WHERE g.id.idusuario = ?1")
    List<GrupoUsuario> getUserGroups (Integer id);
}
