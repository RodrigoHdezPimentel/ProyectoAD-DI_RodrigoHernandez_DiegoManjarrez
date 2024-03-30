package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.Class.LoadConversation;
import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.repositories.ConversacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ArrayList<LoadConversation> getConversacionesByGroupId(Integer id) {
        return conversacionRepository.getConversacionesByGroupId(id);
    }
    public Conversacion saveConversacion(Conversacion conversacion){
        return conversacionRepository.save(conversacion);
    }
    public LoadConversation getLastMessage(Integer id){
        return conversacionRepository.getLastMessage(id);
    }

    public void readMessage(Integer idUsuario, Integer idConversacion){
        conversacionRepository.readMessage(idUsuario, idConversacion);
    }
    public void updateContent(Integer idConv, String contenido){
        conversacionRepository.updateContent(idConv, contenido);
    }

    public void deleteConversation(Integer id){
        conversacionRepository.deleteById(id);
    }

}
