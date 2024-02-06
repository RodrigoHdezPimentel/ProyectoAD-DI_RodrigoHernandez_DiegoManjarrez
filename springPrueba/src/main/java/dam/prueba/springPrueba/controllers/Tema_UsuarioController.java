package dam.prueba.springPrueba.controllers;


import dam.prueba.springPrueba.models.Chat;
import dam.prueba.springPrueba.models.Tema_Usuario;
import dam.prueba.springPrueba.servicies.ChatService;
import dam.prueba.springPrueba.servicies.Tema_UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/TemaUsuario")
public class Tema_UsuarioController {
    @Autowired
    private Tema_UsuarioService teUsService;
    @GetMapping("/all")
    public List<Tema_Usuario> getAllTeUs(){
        return teUsService.getAllTU();
    }
    @GetMapping("/id/{id}")
    public Optional<Tema_Usuario> getTeUsById(@PathVariable Integer id){
        return teUsService.getTUById(id);
    }
    @PostMapping("/save")
    public Tema_Usuario saveTeUs(@RequestBody Tema_Usuario TeUs){
        return teUsService.saveTU(TeUs);
    }

    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteTeUs(@PathVariable Integer id){
        return teUsService.deleteTU(id);
    }


}
