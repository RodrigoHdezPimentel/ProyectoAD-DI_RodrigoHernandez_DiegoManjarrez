package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.Class.ChatLastMessage;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.servicies.GrupoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/getListChatFromUser/{id}")
    public List<ChatLastMessage> getListChatFromUser(@PathVariable Integer id){
        List<ChatLastMessage> listaFiltrada =  grupoUsuarioService.getListChatFromUser(id);
        List<ChatLastMessage> chatSinMensajes = grupoUsuarioService.getListChatUserWhitoutMessage(id);

       for (int i = 0; i < listaFiltrada.size(); i++){
           if(!Objects.equals(listaFiltrada.get(i).getChat().getId().getIdusuario(), id)){
               listaFiltrada.get(i).setChat(grupoUsuarioService.asignarUserChat(id, listaFiltrada.get(i).getChat().getId().getIdgrupo()));
           }
       }
       if(!chatSinMensajes.isEmpty()){
           listaFiltrada.addAll(chatSinMensajes);
       }
        return listaFiltrada;
    }
    @GetMapping("/getListChatUserWhitoutMessage/{id}")
    public List<ChatLastMessage> getListChatUserWhitoutMessage(@PathVariable Integer id){
        return grupoUsuarioService.getListChatUserWhitoutMessage(id);
    }

    @GetMapping("/asignarUserChat/{idU}/{idG}")
    public GrupoUsuario asignarUserChat(@PathVariable Integer idU,@PathVariable Integer idG){
        return grupoUsuarioService.asignarUserChat(idU, idG);
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
    @GetMapping("/rejoinChat/{id}")
    public GrupoUsuario rejoinChat(@PathVariable Integer id) {
        grupoUsuarioService.rejoinChat(id);
        return grupoUsuarioService.getById(id);
    }
    @GetMapping("/findByIdUserIdGroup/{idUsuario}/{idGrupo}")
    public GrupoUsuario findByIdUserIdGroup(@PathVariable Integer idUsuario, @PathVariable Integer idGrupo){
        return grupoUsuarioService.asignarUserChat(idUsuario, idGrupo);
    }
    @GetMapping("/salirGrupo/{idGrupoUsuario}/{fecha}")
    public void salirGrupo(@PathVariable Integer idGrupoUsuario, @PathVariable String fecha){
        grupoUsuarioService.salirGrupo(idGrupoUsuario, fecha);
    }

}
