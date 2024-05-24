package dam.prueba.spring_boot_foroex.servicies;

import dam.prueba.spring_boot_foroex.models.Grupo;
import dam.prueba.spring_boot_foroex.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class    GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> getAllGrupo() {
        return grupoRepository.findAll();
    }

    public Optional<Grupo> getGrupoById(Integer id) {
        return grupoRepository.findById(id);
    }

    public boolean deleteGroup(Integer id){
        grupoRepository.deleteById(id);
        return grupoRepository.findById(id).isEmpty();
    }

    public Grupo saveGrupo(Grupo grupo){
        return grupoRepository.save(grupo);
    }
    public Grupo findGroup(String codigo){
        return grupoRepository.findGroup(codigo);
    }
    public boolean findUserInGroup(String codigo, Integer idU){
        return grupoRepository.findUserInGroup(codigo, idU)>0;
    }


}
