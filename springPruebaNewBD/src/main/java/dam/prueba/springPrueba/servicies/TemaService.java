package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Tema;
import dam.prueba.springPrueba.repositories.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemaService {
    @Autowired
    private TemaRepository temaRepository;

    public List<Tema> getAllTemas() {
        return temaRepository.findAll();
    }

    public Optional<Tema> getTemaById(Integer id) {
        return temaRepository.findById(id);
    }

    public Tema saveTema(Tema tema){
        return temaRepository.save(tema);
    }
    public Boolean deleteTema(Integer id){
        temaRepository.deleteById(id);
        return temaRepository.findById(id).isEmpty();
    }


}