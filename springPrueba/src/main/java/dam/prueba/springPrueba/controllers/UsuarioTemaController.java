package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.UsuarioTema;
import dam.prueba.springPrueba.servicies.UsuarioTemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
