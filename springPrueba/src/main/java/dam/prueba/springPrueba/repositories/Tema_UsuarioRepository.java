package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Chat;
import dam.prueba.springPrueba.models.PrimaryKey;
import dam.prueba.springPrueba.models.Tema_Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tema_UsuarioRepository extends JpaRepository<Tema_Usuario, PrimaryKey> {

}
