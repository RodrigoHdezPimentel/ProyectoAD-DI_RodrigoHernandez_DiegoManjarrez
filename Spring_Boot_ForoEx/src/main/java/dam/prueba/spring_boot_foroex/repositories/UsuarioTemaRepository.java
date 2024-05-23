package dam.prueba.spring_boot_foroex.repositories;

import dam.prueba.spring_boot_foroex.models.UsuarioTema;
import dam.prueba.spring_boot_foroex.models.UsuarioTemaFK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsuarioTemaRepository extends JpaRepository<UsuarioTema, UsuarioTemaFK> {

    @Query(value = "SELECT ut FROM UsuarioTema ut WHERE ut.id.idUsuario = ?1")
    List<UsuarioTema> getAllTemaFromId (Integer id);
    @Modifying
    @Transactional
    @Query("DELETE FROM UsuarioTema WHERE id.idTema = ?1 and id.idUsuario = ?2")
    Integer removeTemaUser(Integer themeId, Integer userId);
}
