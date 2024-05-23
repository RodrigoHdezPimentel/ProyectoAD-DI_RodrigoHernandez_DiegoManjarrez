package dam.prueba.spring_boot_foroex.servicies;

import dam.prueba.spring_boot_foroex.Class.Message;
import dam.prueba.spring_boot_foroex.models.Conversacion;
import dam.prueba.spring_boot_foroex.repositories.ConversacionRepository;
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
    public ArrayList<Message> getConversacionesByGroupId(Integer id) {
        return conversacionRepository.getConversacionesByGroupId(id);
    }
    public Conversacion saveConversacion(Conversacion conversacion){
        return conversacionRepository.save(conversacion);
    }
    public Message getLastMessage(Integer id){
        return conversacionRepository.getLastMessage(id);
    }

    public void readMessages(Integer idUsuario, Integer idGrupo){
        conversacionRepository.readMessages(idUsuario, idGrupo);
    }
    public void updateContent(Integer idConv, String contenido){
        conversacionRepository.updateContent(idConv, contenido);
    }

    public void deleteConversation(Integer id){
        conversacionRepository.deleteById(id);
    }

}
