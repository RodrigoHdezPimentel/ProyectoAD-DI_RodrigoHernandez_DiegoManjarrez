package dam.prueba.springPrueba.controllers;

import dam.prueba.springPrueba.models.Grupo;
import dam.prueba.springPrueba.models.Like;
import dam.prueba.springPrueba.repositories.GrupoRepository;
import dam.prueba.springPrueba.servicies.GrupoService;
import dam.prueba.springPrueba.servicies.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
