package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.UsuarioTema;
import dam.prueba.springPrueba.servicies.GrupoUsuarioService;
import dam.prueba.springPrueba.servicies.UsuarioTemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupoUsuario")
public class GrupoUsuarioController {
    @Autowired
    private GrupoUsuarioService grupoUsuarioService;
    @GetMapping("/all")
    public List<GrupoUsuario> getAllGrupoUsuario(){
        return grupoUsuarioService.getAllGrupoUsuario();
    }

    @PostMapping("/save")
    public GrupoUsuario saveGrupoUsuario(@RequestBody GrupoUsuario userTema){
        return grupoUsuarioService.saveGrupoUsuario(userTema);
    }
    @GetMapping("/getUserGroups/{id}")
    public List<GrupoUsuario> getUserGroups(@PathVariable Integer id){
        return grupoUsuarioService.getUserGroups(id);
    }

}
