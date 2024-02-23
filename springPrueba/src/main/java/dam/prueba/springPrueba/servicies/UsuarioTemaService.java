package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.models.UsuarioTema;
import dam.prueba.springPrueba.repositories.UsuarioRepository;
import dam.prueba.springPrueba.repositories.UsuarioTemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioTemaService {
    @Autowired
    private UsuarioTemaRepository usuarioTemaRepository;
    public List<UsuarioTema> getAllUsuarioTema() {
        return usuarioTemaRepository.findAll();
    }
    public List<UsuarioTema> getAllTemaFromId(Integer id) {
        return usuarioTemaRepository.getAllTemaFromId(id);
    }

    public UsuarioTema saveUserTema(UsuarioTema userTema){
        return usuarioTemaRepository.save(userTema);
    }
    public Boolean  removeTemaUser(Integer idT, Integer idU){
        return usuarioTemaRepository.removeTemaUser(idT, idU) > 0 ;
    }



}
