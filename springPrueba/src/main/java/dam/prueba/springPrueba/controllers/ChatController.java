package dam.prueba.springPrueba.controllers;


import dam.prueba.springPrueba.models.Chat;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.servicies.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @GetMapping("/getUserChats/{id}")
    public List<Usuario> getUserChats(@PathVariable Integer id){
        ArrayList<Usuario> listaUser = (ArrayList<Usuario>) chatService.getUserChats(id);
        ArrayList<Usuario> listaUserFiltered = new ArrayList<>();

        for(Usuario u : listaUser){
            if(!listaUserFiltered.contains(u)){
                listaUserFiltered.add(u);
            }
        }
        return listaUserFiltered;
    }

    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteChat(@PathVariable Integer id){
        return chatService.deleteChat(id);
    }

}
