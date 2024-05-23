package dam.prueba.spring_boot_foroex.servicies;

import dam.prueba.spring_boot_foroex.Class.ChatListUser;
import dam.prueba.spring_boot_foroex.models.GrupoUsuario;
import dam.prueba.spring_boot_foroex.models.Usuario;
import dam.prueba.spring_boot_foroex.repositories.GrupoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoUsuarioService {
    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;
    public List<GrupoUsuario> getAllGrupoUsuario() {
        return grupoUsuarioRepository.findAll();
    }

    public GrupoUsuario saveGrupoUsuario(GrupoUsuario grupoUser){
        return grupoUsuarioRepository.save(grupoUser);
    }

    public List<ChatListUser> getListChatFromUser(Integer id) {
        return grupoUsuarioRepository.getListChatFromUser(id);
    }
    public List<ChatListUser> getListChatUserWhitoutMessage(Integer id) {
        return grupoUsuarioRepository.getListChatUserWhitoutMessage(id);
    }

    public GrupoUsuario asignarUserChat(Integer idU, Integer idG) {
        return grupoUsuarioRepository.asignarUserChat(idU, idG);
    }
    public Integer numMessageNews(Integer idG, Integer idU) {
        return grupoUsuarioRepository.numMessageNews(idG, idU);
    }

    public GrupoUsuario getById(Integer id) {
        return grupoUsuarioRepository.getById(id);
    }

    public List<Usuario> getGroupUsers(Integer id) {
        return grupoUsuarioRepository.getGroupUsers(id);
    }
    public List<List<Integer>> getLoadChat(Integer idU, Integer idV) {
        return grupoUsuarioRepository.getLoadChat(idU, idV);
    }
    public String pathFotoUserChat(Integer idU,Integer idG) {
        return grupoUsuarioRepository.pathFotoUserChat(idU,idG);
    }


    public List<String> getGroupName(Integer idGr, Integer idUs) {
        return grupoUsuarioRepository.getGroupName(idGr, idUs);
    }
    public void updateGroupName(String newName, Integer id){
         grupoUsuarioRepository.updateGroupName(newName, id);
    }
    public void updateChatName(String newName, Integer id){
        grupoUsuarioRepository.updateChatName(newName, id);
    }

    public void rejoinChat(Integer idGrupoUsuario){
        grupoUsuarioRepository.rejoinChat(idGrupoUsuario);
    }

    public void salirGrupo(Integer idGrupoUsuario, String fecha){
        grupoUsuarioRepository.salirGrupo(idGrupoUsuario, fecha);
    }
}
