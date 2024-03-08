package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.servicies.ConversacionService;
import dam.prueba.springPrueba.servicies.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
