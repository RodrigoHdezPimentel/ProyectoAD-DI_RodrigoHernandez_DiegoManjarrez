package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Chat;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Optional<Chat> getChatById(Integer id) {
        return chatRepository.findById(id);
    }

    public Chat saveChat(Chat chat){
        return chatRepository.save(chat);
    }
    public Boolean deleteChat(Integer id){
        chatRepository.deleteById(id);
        return chatRepository.findById(id).isEmpty();
    }
    public List<Chat> getUserChats(Integer id){
        return chatRepository.getUserChats(id);
    }

    public  List<Chat> getUsersConversation(Integer id1){
        return  chatRepository.getUsersConversation(id1);
    }


}