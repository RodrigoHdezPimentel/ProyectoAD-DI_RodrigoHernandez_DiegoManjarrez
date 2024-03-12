package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Integer> {

    //lista los grupos al que pertenece el usuario
    @Query(value = "SELECT g FROM GrupoUsuario g " +
            "WHERE g.idusuario = ?1")
    List<GrupoUsuario> getUserGroups (Integer id);

    //solo optiene una datos en especifico
    @Query(value = "SELECT g FROM GrupoUsuario g " +
            "WHERE g.idgrupousuario = ?1")
    GrupoUsuario getById(Integer id);

    @Query(value = "SELECT u FROM GrupoUsuario g JOIN Usuario u on u.idusuario = g.idusuario " +
            "WHERE g.idgrupo = ?1")
    List<Usuario> getGroupUsers(Integer id);

    //PARA SACAR LOS GRUPOS EN COMÚN, SACO SOLAMENT EL idGrupo
    @Query(value = "SELECT g1.idgrupo, g1.idgrupousuario FROM GrupoUsuario g1 JOIN Grupo g ON g1.idgrupo = g.idgrupo JOIN GrupoUsuario g2 " +
            "ON g2.idgrupo = g.idgrupo WHERE g1.idusuario = ?1 AND g2.idusuario = ?2 GROUP BY g.idgrupo")
    List<List<Integer>> getCommonGroups(Integer idU, Integer idV);
    //Para contar el numero de miembros dentro de un grupo (idGrupo)
    @Query(value = "SELECT COUNT(DISTINCT u) FROM GrupoUsuario g JOIN Usuario u on u.idusuario = g.idusuario " +
            "WHERE g.idgrupo = ?1")
    Integer getNumberUsers(Integer id);

}
