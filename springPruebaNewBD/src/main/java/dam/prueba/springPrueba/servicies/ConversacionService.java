package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.repositories.ConversacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversacionService {
    @Autowired
    private ConversacionRepository conversacionRepository;

    public List<Conversacion> getAllConversacion() {
        return conversacionRepository.findAll();
    }

    public Optional<Conversacion> getConversacionById(Integer id) {
        return conversacionRepository.findById(id);
    }
    public List<Conversacion> getConversacionesByGroupId(Integer id) {
        return conversacionRepository.getConversacionesByGroupId(id);
    }

}
