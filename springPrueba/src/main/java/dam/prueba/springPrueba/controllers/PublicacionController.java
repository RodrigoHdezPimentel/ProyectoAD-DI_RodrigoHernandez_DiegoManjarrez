package dam.prueba.springPrueba.controllers;


import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.servicies.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {
    @Autowired
    private PublicacionService publicacionService;
    @GetMapping("/all")
    public List<Publicacion> getAllUsuarios(){
        return publicacionService.getAllPublicaciones();
    }
    @GetMapping("/id/{id}")
    public Optional<Publicacion> getUsuarioById(@PathVariable Integer id){
        return publicacionService.getPublicacionById(id);
    }
    @PostMapping("/save")
    public Publicacion savePublicacion(@RequestBody Publicacion publicacion){
        return publicacionService.savePublicacion(publicacion);
    }
    @GetMapping("/allContentFromUser/{id}")
    public List<String> getAllPublishesFromUser(@PathVariable Integer id){
        List<Publicacion> publicaciones = publicacionService.getAllPublicaciones();
        List<String> filterPublicaiones = new ArrayList<String>();

        for(Publicacion p : publicaciones){
            if(p.getIdusuario() == id){
                filterPublicaiones.add(p.getContenido());
            }
        }
        return  filterPublicaiones;
    }
    @GetMapping("/allComentariosFromPublicacion/{id}")
    public List<String> getAllComentsFromPublish(@PathVariable Integer id){
        List<Publicacion> publicaciones = publicacionService.getAllPublicaciones();
        List<String> filterPublicaiones = new ArrayList<String>();

        for(Publicacion p : publicaciones){
            if(p.getIdpublirefer() == id){
                filterPublicaiones.add(p.getContenido());
            }
        }
        return  filterPublicaiones;
    }
    @GetMapping("/searchInName/{str}")
    public List<Publicacion> getsearchName(@PathVariable String str){
        List<Publicacion> publicaciones = publicacionService.getAllPublicaciones();
        List<Publicacion> publicacionesFiltered = new ArrayList<Publicacion>();

        for(Publicacion p : publicaciones){
            if(p.getContenido().toLowerCase().contains(str.toLowerCase())){
                publicacionesFiltered.add(p);
            }
        }
        return  publicacionesFiltered;
    }
    @DeleteMapping("/deleteById/{id}")
    public Boolean deletePublicacion(@PathVariable Integer id){
        return publicacionService.deleteUsuario(id);
    }


}
