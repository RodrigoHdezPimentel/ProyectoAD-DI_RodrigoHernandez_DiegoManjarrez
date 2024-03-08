package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Conversaciones;
import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.repositories.ConversacionesRepository;
import dam.prueba.springPrueba.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversacionesService {
    @Autowired
    private ConversacionesRepository conversacionesRepository;

    public List<Conversaciones> getAllConversaciones() {
        return conversacionesRepository.findAll();
    }

    public Optional<Conversaciones> getConversacionesById(Integer id) {
        return conversacionesRepository.getConversacionesById(id);
    }
}
