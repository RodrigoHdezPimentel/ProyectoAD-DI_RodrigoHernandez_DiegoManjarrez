package dam.prueba.spring_boot_foroex.servicies;

import dam.prueba.spring_boot_foroex.models.Publicacion;
import dam.prueba.spring_boot_foroex.models.Usuario;
import dam.prueba.spring_boot_foroex.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void deleteUsuario(Integer id, String fecha){
        usuarioRepository.deleteUsuario(id, fecha);
    }
    public List<String> getByName(String nombre) {
       return usuarioRepository.getByNombre(nombre);
    }
    public List<Publicacion> getUserPublicacion (Integer id){ return  usuarioRepository.getUserPublicacion(id);}

    public List<Publicacion> getUserPublicacionFromTema(Integer id){ return  usuarioRepository.getUserPublicacionFromTema(id);}
    public List<Publicacion> getUserPublicacionFromLike(Integer id){ return  usuarioRepository.getUserPublicacionFromLike(id);}

    public Optional<Usuario> getUserRegister(String name, String pass){ return  usuarioRepository.getUserRegister(name, pass);}
    public void actualizarFoto(Integer id, String nombreFoto){
        usuarioRepository.fotoUsuario(id, nombreFoto);
    }

    //------poner fotos Usuarios---
    public void fotosUsuarios(Integer idU, String image){
         usuarioRepository.fotoUsuario(idU,image);
    }


    //-----------------------

}