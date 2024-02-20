package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public Usuario updateUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public Boolean deleteUsuario(Integer id){
        usuarioRepository.deleteById(id);
        return usuarioRepository.findById(id).isEmpty();
    }
    public List<String> getByName(String nombre) {
       return usuarioRepository.getByNombre(nombre);
    }
    public List<Publicacion> getUserPublicacion (Integer id){ return  usuarioRepository.getUserPublicacion(id);}

    public List<Publicacion> getUserPublicacionFromTema(Integer id){ return  usuarioRepository.getUserPublicacionFromTema(id);}
    public List<Publicacion> getUserPublicacionFromLike(Integer id){ return  usuarioRepository.getUserPublicacionFromLike(id);}


    //public List<Publicacion> getUserLike (Integer id){ return  usuarioRepository.getUserLike(id);}

}