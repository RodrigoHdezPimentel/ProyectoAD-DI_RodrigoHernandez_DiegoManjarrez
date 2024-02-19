package dam.prueba.springPrueba.repositories;

import dam.prueba.springPrueba.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    //QUERY PA SACAR A LOS USUARIOS QUE HAN ABIERTO UN CHAT CON EL USUARIO REGISTRADO
//    SELECT u.*, c.idUsuarioOrigen FROM usuarios u inner join chat c on c.idUsuarioDestino
//    = u.idUsuario WHERE c.idUsuarioOrigen = 2;



}
