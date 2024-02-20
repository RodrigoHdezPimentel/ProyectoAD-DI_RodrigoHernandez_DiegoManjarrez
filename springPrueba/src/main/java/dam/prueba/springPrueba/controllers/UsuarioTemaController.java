package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.models.UsuarioTema;
import dam.prueba.springPrueba.servicies.UsuarioTemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarioTema")
public class UsuarioTemaController {
    @Autowired
    private UsuarioTemaService usuarioTemaService;
    @GetMapping("/all")
    public List<UsuarioTema> getAllUsuarioTema(){
        return usuarioTemaService.getAllUsuarioTema();
    }


    @PostMapping("/save")
    public UsuarioTema saveUserTema(@RequestBody UsuarioTema userTema){
        return usuarioTemaService.saveUserTema(userTema);
    }

}
