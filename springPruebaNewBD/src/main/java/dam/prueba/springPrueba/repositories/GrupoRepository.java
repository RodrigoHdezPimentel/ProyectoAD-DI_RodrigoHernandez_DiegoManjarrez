package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.GrupoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    @Query(value = "SELECT g FROM Grupo g " +
            "WHERE g.codigo = ?1")
    Grupo findGroup(String codigo);



}
