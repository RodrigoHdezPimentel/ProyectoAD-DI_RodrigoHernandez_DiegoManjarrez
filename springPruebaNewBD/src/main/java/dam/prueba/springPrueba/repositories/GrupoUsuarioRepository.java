package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.GrupoUsuarioFK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, GrupoUsuarioFK> {

}
