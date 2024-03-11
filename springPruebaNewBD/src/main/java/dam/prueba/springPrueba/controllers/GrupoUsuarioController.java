package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.Usuario;
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

    @GetMapping("/getById/{id}")
    public GrupoUsuario getById(@PathVariable Integer id){
        return grupoUsuarioService.getById(id);
    }
    @GetMapping("/getGroupUsers/{id}")
    public List<Usuario> getGroupUsers(@PathVariable Integer id){
        return grupoUsuarioService.getGroupUsers(id);
    }
    @GetMapping("/getCommonGroups/{idU}/{idV}")
    public List<List<Integer>> getCommonGroups(@PathVariable Integer idU,@PathVariable Integer idV){
        return grupoUsuarioService.getCommonGroups(idU,idV);
    }
    @GetMapping("/getNumberUsers/{id}")
    public Boolean getNumberUsers(@PathVariable Integer id){
        return grupoUsuarioService.getNumberUsers(id)==2;
    }


}
