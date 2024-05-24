package dam.prueba.spring_boot_foroex.controllers;

import dam.prueba.spring_boot_foroex.models.Grupo;
import dam.prueba.spring_boot_foroex.servicies.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grupo")
public class GrupoController {
    @Autowired
    private GrupoService grupoService;
    @GetMapping("/all")
    public List<Grupo> getAllGrupos(){
        return grupoService.getAllGrupo();
    }
    @PostMapping("/save")
    public Grupo saveGrupo(@RequestBody Grupo grupo){
        return grupoService.saveGrupo(grupo);
    }

    @GetMapping("/id/{id}")
    public Optional<Grupo> getGrupoById(@PathVariable Integer id){
        return grupoService.getGrupoById(id);
    }
    @GetMapping("/deleteGroup/{id}")
    public boolean deleteGroup(@PathVariable Integer id){
        return grupoService.deleteGroup(id);
    }
    @GetMapping("/findGroup/{codigo}")
    public Grupo findGroup(@PathVariable String codigo){
        return grupoService.findGroup(codigo);
    }
    @GetMapping("/findUserInGroup/{codigo}/{idU}")
    public boolean findUserInGroup(@PathVariable String codigo, @PathVariable Integer idU){
        return grupoService.findUserInGroup(codigo,idU);
    }



    //QUERY PARA COLOCAR UNA FOTO A TODO LOS GRUPOS
//    UPDATE grupos set foto = (SELECT foto from usuarios WHERE idUsuario=10) Where foto = "Ruta";
}
