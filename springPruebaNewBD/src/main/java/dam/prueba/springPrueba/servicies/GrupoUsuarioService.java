package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.Class.ChatLastMessage;
import dam.prueba.springPrueba.models.GrupoUsuario;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.repositories.GrupoUsuarioRepository;
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

    public List<ChatLastMessage> getListChatFromUser(Integer id) {
        return grupoUsuarioRepository.getListChatFromUser(id);
    }
    public List<ChatLastMessage> getListChatUserWhitoutMessage(Integer id) {
        return grupoUsuarioRepository.getListChatUserWhitoutMessage(id);
    }

    public GrupoUsuario asignarUserChat(Integer idU, Integer idG) {
        return grupoUsuarioRepository.asignarUserChat(idU, idG);
    }

    public GrupoUsuario getById(Integer id) {
        return grupoUsuarioRepository.getById(id);
    }

    public List<Usuario> getGroupUsers(Integer id) {
        return grupoUsuarioRepository.getGroupUsers(id);
    }
    public List<List<Integer>> getCommonGroups(Integer idU, Integer idV) {
        return grupoUsuarioRepository.getCommonGroups(idU, idV);
    }
    public Integer getNumberUsers(Integer id) {
        return grupoUsuarioRepository.getNumberUsers(id);
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
