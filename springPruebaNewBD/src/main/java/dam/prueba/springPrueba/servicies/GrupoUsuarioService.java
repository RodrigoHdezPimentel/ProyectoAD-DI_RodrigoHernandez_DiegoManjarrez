package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.UsuarioTema;
import dam.prueba.springPrueba.repositories.GrupoUsuarioRepository;
import dam.prueba.springPrueba.repositories.UsuarioTemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoUsuarioService {
    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;
    public List<GrupoUsuario> getAllGrupoUsuario() {
        return grupoUsuarioRepository.findAll();
    }

    public GrupoUsuario saveGrupoUsuario(GrupoUsuario GrupoUser){
        return grupoUsuarioRepository.save(GrupoUser);
    }
}
