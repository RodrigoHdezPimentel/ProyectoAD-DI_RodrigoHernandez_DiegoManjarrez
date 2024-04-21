package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.Class.LoadConversation;
import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.servicies.ConversacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conversacion")
public class ConversacionController {
    @Autowired
    private ConversacionService conversacionService;
    @GetMapping("/all")
    public List<Conversacion> getAllGrupos(){
        return conversacionService.getAllConversacion();
    }
    @GetMapping("/id/{id}")
    public Optional<Conversacion> getGrupoById(@PathVariable Integer id){
        return conversacionService.getConversacionById(id);
    }
    @GetMapping("/getConversacionesByGroupId/{id}")
    public ArrayList<LoadConversation> getConversacionesByGroupId(@PathVariable Integer id){
        return conversacionService.getConversacionesByGroupId(id);
    }
    @PostMapping("/save")
    public Conversacion saveConversacion(@RequestBody Conversacion conversacion){
        return conversacionService.saveConversacion(conversacion);
    }
    @GetMapping("/getLastMessage/{id}")
    public LoadConversation getLastMessage(@PathVariable Integer id){
        return conversacionService.getLastMessage(id);
    }

    @GetMapping("/readMessages/{idUsuario}/{idGrupo}")
    public void readMessage(@PathVariable Integer idUsuario, @PathVariable Integer idGrupo){
        conversacionService.readMessages(idUsuario, idGrupo);
    }

    @GetMapping("/updateContent/{idConv}/{contenido}")
    public void updateContent(@PathVariable Integer idConv, @PathVariable String contenido){
        conversacionService.updateContent(idConv, contenido);
    }

    @GetMapping("/deleteConversation/{idConv}")
    public void deleteConversation(@PathVariable Integer idConv){
        conversacionService.deleteConversation(idConv);
    }

}
