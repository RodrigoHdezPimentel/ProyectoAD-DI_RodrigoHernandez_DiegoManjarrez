package dam.prueba.spring_boot_foroex.repositories;

import dam.prueba.spring_boot_foroex.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    @Query(value = "SELECT g FROM Grupo g " +
            "WHERE g.codigo = ?1")
    Grupo findGroup(String codigo);



}
