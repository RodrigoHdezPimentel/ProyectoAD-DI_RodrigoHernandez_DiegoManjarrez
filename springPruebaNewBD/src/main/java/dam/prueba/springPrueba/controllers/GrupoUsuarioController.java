package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.Class.ChatLastMessage;
import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.servicies.GrupoUsuarioService;
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
    public GrupoUsuario saveGrupoUsuario(@RequestBody GrupoUsuario grupoUser){
        return grupoUsuarioService.saveGrupoUsuario(grupoUser);
    }


    @GetMapping("/getUserGroups/{id}")
    public List<ChatLastMessage> getUserGroups(@PathVariable Integer id){
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

    @GetMapping("/getGroupName/{idGr}/{idUs}")
    public String getGroupName(@PathVariable Integer idGr,@PathVariable Integer idUs){
        if(grupoUsuarioService.getGroupName(idGr, idUs).isEmpty()){
            return null;
        }
        return grupoUsuarioService.getGroupName(idGr, idUs).get(0);
    }

    @GetMapping("/updateGroupName/{newName}/{id}")
    public void updateGroupName(@PathVariable String newName,@PathVariable Integer id) {
        //Si es un chat o no
            if (grupoUsuarioService.getGroupUsers(
                    grupoUsuarioService.getById(id).getId().getIdgrupo()).size() == 2) {
                grupoUsuarioService.updateChatName(newName, id);
            } else {
                grupoUsuarioService.updateGroupName(newName, id);
            }

    }
}
