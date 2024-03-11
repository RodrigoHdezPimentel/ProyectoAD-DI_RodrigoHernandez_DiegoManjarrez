package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.servicies.ConversacionService;
import dam.prueba.springPrueba.servicies.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Conversacion> getConversacionesByGroupId(@PathVariable Integer id){
        return conversacionService.getConversacionesByGroupId(id);
    }
    @PostMapping("/save")
    public Conversacion saveConversacion(@RequestBody Conversacion conversacion){
        return conversacionService.saveConversacion(conversacion);
    }
    @GetMapping("/getLastMessage/{id}")
    public Conversacion getLastMessage(@PathVariable Integer id){
        return conversacionService.getLastMessage(id);
    }

}
