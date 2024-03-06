package dam.prueba.springPrueba.controllers;


import dam.prueba.springPrueba.models.Chat;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.servicies.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @GetMapping("/all")
    public List<Chat> getAllUChats(){
        return chatService.getAllChats();
    }
    @GetMapping("/id/{id}")
    public Optional<Chat> getChatById(@PathVariable Integer id){
        return chatService.getChatById(id);
    }
    @PostMapping("/save")
    public Chat saveChat(@RequestBody Chat chat){
        return chatService.saveChat(chat);
    }
    @GetMapping("/searchInChat/{id}/{str}")
    public List<Chat> getsearchChat(@PathVariable Integer id, @PathVariable String str){
        List<Chat> chats = chatService.getAllChats();
        List<Chat> chatsFiltered = new ArrayList<Chat>();

        for(Chat c : chats){
            if(c.getIdDestino() == id || c.getIdOrigen() == id){
                if(c.getContenido().toLowerCase().contains(str.toLowerCase())){
                    chatsFiltered.add(c);
                }
            }
        }
        return  chatsFiltered;
    }
    //Filtra los chat en donde muestra el ultimo mensaje con su ultima fecha
//    @GetMapping("/getUserChatsFilter/{id}")
//    public Chat getUserChatsFilter(@PathVariable Integer id){
//        return chatService.getUserChatsFilter(id);
//    }
    @GetMapping("/getUserChats/{id}")
    public List<Chat> getUserChats(@PathVariable Integer id){
        ArrayList<Chat> listaChat = (ArrayList<Chat>) chatService.getUserChats(id);
        ArrayList<Chat> listaChatFilter = new ArrayList<>();

        listaChatFilter.add(listaChat.get(0));

        for(int x = 0; x < listaChat.size(); x++){
            if(Objects.equals(listaChat.get(x).getIdDestino(), id)){
                for(int y = 0; y < listaChatFilter.size(); y++){
                    if(listaChat.get(x).getUsuarioOr() == listaChatFilter.get(y).getUsuarioDes()){
                        break;
                    }else{
                        if(y == listaChatFilter.size() - 1){
                            listaChatFilter.add(listaChat.get(x));
                            break;
                        }
                    }
                }
            }else{
                for(int y = 0; y < listaChatFilter.size(); y++){
                    if(listaChat.get(x).getUsuarioDes() == listaChatFilter.get(y).getUsuarioOr()){
                        break;
                    }else{
                        if(y == listaChat.size() - 1){
                            listaChatFilter.add(listaChat.get(x));
                            break;
                        }
                    }
                }
            }
        }
        return listaChatFilter;
    }

    @GetMapping("/getUsersConversation/{id1}/{id2}")
    public  List<Chat> getUsersConversation(@PathVariable Integer id1, @PathVariable Integer id2){
        ArrayList<Chat> listaChat = (ArrayList<Chat>) chatService.getUsersConversation(id1);
        ArrayList<Chat> listaChatFilter = new ArrayList<>();

        for(Chat c : listaChat){
            if(Objects.equals(c.getIdDestino(), id1)){
                if(Objects.equals(c.getIdOrigen(), id2)){
                    listaChatFilter.add(c);
                }
            }else{
                if(Objects.equals(c.getIdDestino(), id2)){
                    listaChatFilter.add(c);
                }
            }
        }
        return  listaChatFilter;
    }

    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteChat(@PathVariable Integer id){
        return chatService.deleteChat(id);
    }

}
