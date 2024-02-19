package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.UsuarioTema;
import dam.prueba.springPrueba.models.UsuarioTemaFK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioTemaRepository extends JpaRepository<UsuarioTema, UsuarioTemaFK> {


}
