package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.Like;
import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.repositories.GrupoRepository;
import dam.prueba.springPrueba.servicies.GrupoService;
import dam.prueba.springPrueba.servicies.LikeService;
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
    @GetMapping("/id/{id}")
    public Optional<Grupo> getGrupoById(@PathVariable Integer id){
        return grupoService.getGrupoById(id);
    }
    @GetMapping("/deleteGroup/{id}")
    public boolean deleteGroup(@PathVariable Integer id){
        return grupoService.deleteGroup(id);
    }
    @PostMapping("/save")
    public Grupo saveGrupo(@RequestBody Grupo grupo){
        return grupoService.saveGrupo(grupo);
    }
}
