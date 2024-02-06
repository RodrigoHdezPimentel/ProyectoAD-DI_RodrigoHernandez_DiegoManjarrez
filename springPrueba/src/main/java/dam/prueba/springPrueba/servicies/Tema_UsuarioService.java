package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Tema_Usuario;
import dam.prueba.springPrueba.repositories.Tema_UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Tema_UsuarioService {
    @Autowired
    private Tema_UsuarioRepository teUsRepository;

    public List<Tema_Usuario> getAllTU() {
        return teUsRepository.findAll();
    }

    public Optional<Tema_Usuario> getTUById(Integer id) {
        return teUsRepository.findById(id);
    }

    public Tema_Usuario saveTU(Tema_Usuario teUs){
        return teUsRepository.save(teUs);
    }
    public Boolean deleteTU(Integer id){
        teUsRepository.deleteById(id);
        return teUsRepository.findById(id).isEmpty();
    }


}