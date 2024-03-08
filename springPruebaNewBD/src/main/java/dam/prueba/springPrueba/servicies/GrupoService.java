package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.Like;
import dam.prueba.springPrueba.repositories.GrupoRepository;
import dam.prueba.springPrueba.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> getAllGrupo() {
        return grupoRepository.findAll();
    }

    public Optional<Grupo> getGrupoById(Integer id) {
        return grupoRepository.findById(id);
    }
}
