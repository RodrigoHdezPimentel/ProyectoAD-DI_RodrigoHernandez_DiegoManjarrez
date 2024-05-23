package dam.prueba.spring_boot_foroex.controllers;

import dam.prueba.spring_boot_foroex.Class.ChatListUser;
import dam.prueba.spring_boot_foroex.models.GrupoUsuario;
import dam.prueba.spring_boot_foroex.models.Usuario;
import dam.prueba.spring_boot_foroex.servicies.GrupoUsuarioService;
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
    public List<ChatListUser> getListChatFromUser(@PathVariable Integer id){
        List<ChatListUser> listaFiltrada =  grupoUsuarioService.getListChatFromUser(id);
        List<ChatListUser> chatSinMensajes = grupoUsuarioService.getListChatUserWhitoutMessage(id);

       for (int i = 0; i < listaFiltrada.size(); i++){
           if(!Objects.equals(listaFiltrada.get(i).getChat().getId().getIdusuario(), id)){
               listaFiltrada.get(i).setChat(grupoUsuarioService.asignarUserChat(id, listaFiltrada.get(i).getChat().getId().getIdgrupo()));
           }
           //PARA LOS CHAT QUE TENGAN CODIGO NULL
           asignarFotoChat(listaFiltrada.get(i));
           //COLOCAR LA CANTIDAD DE MENSAJES SIN LEER
           mensajesSinLeer(listaFiltrada.get(i));

       }
       if(!chatSinMensajes.isEmpty()){
           for (int i = 0; i < chatSinMensajes.size();i++){
              //PARA LOS CHAT QUE TENGAN CODIGO NULL
                  asignarFotoChat(chatSinMensajes.get(i));
           }
           listaFiltrada.addAll(chatSinMensajes);
       }

        return listaFiltrada;
    }
    @GetMapping("/pathFotoUserChat/{idU}/{idG}")
    public String pathFotoUserChat(@PathVariable Integer idU, @PathVariable Integer idG){

        return grupoUsuarioService.pathFotoUserChat(idU, idG);
    }

    @GetMapping("/getListChatUserWhitoutMessage/{id}")
    public List<ChatListUser> getListChatUserWhitoutMessage(@PathVariable Integer id){
        return grupoUsuarioService.getListChatUserWhitoutMessage(id);
    }

    @GetMapping("/asignarUserChat/{idU}/{idG}")
    public GrupoUsuario asignarUserChat(@PathVariable Integer idU,@PathVariable Integer idG){
        return grupoUsuarioService.asignarUserChat(idU, idG);
    }

    @GetMapping("/numMessageNews/{idG}/{idU}")
    public Integer numMessageNews(@PathVariable Integer idG, @PathVariable Integer idU){
        return grupoUsuarioService.numMessageNews(idG, idU);
    }

    @GetMapping("/getById/{id}")
    public GrupoUsuario getById(@PathVariable Integer id){
        return grupoUsuarioService.getById(id);
    }
    @GetMapping("/getGroupUsers/{id}")
    public List<Usuario> getGroupUsers(@PathVariable Integer id){
        return grupoUsuarioService.getGroupUsers(id);
    }

    @GetMapping("/getLoadChat/{idU}/{idV}")
    public List<List<Integer>> getLoadChat(@PathVariable Integer idU, @PathVariable Integer idV){
    return grupoUsuarioService.getLoadChat(idU, idV);
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

    public void asignarFotoChat(ChatListUser chat){
        if(chat.getChat().getId().getGrupo().getCodigo() == null){
            chat.getChat().getId().getGrupo().setFoto
                    (grupoUsuarioService.pathFotoUserChat(chat.getChat().getId().getIdusuario(),
                            chat.getChat().getId().getIdgrupo()));
        }

    }

    public void mensajesSinLeer(ChatListUser chat){
        chat.setNumNewMessage(grupoUsuarioService.numMessageNews(chat.getChat().getId().getIdgrupo(), chat.getChat().getId().getIdusuario()));
    }

}
