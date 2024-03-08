package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.Conversaciones;
import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.servicies.ConversacionesService;
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
public class ConversacionesController {
    @Autowired
    private ConversacionesService conversacionesService;
    @GetMapping("/all")
    public List<Conversaciones> getAllConversaciones(){
        return conversacionesService.getAllConversaciones();
    }
    @GetMapping("/id/{id}")
    public Optional<Conversaciones> getConversacionesById(@PathVariable Integer id){
        return conversacionesService.getConversacionesById(id);
    }
}
