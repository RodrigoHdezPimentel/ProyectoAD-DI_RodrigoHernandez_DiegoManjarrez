package dam.prueba.springPrueba.controllers;


import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.servicies.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {
    @Autowired
    private PublicacionService publicacionService;
    @GetMapping("/all")
    public List<Publicacion> getAll(){
        return publicacionService.getAllPublicaciones();
    }
    @GetMapping("/id/{id}")
    public Optional<Publicacion> getPublishById(@PathVariable Integer id){
        return publicacionService.getPublicacionById(id);
    }
    @PostMapping("/save")
    public Publicacion savePublish(@RequestBody Publicacion publicacion){
        return publicacionService.savePublicacion(publicacion);
    }
    @GetMapping("/allContentFromUser/{id}")
    public List<String> getAllPublishesFromUser(@PathVariable Integer id){
        List<Publicacion> publicaciones = publicacionService.getAllPublicaciones();
        List<String> filterPublicaiones = new ArrayList<String>();

        for(Publicacion p : publicaciones){
            if(Objects.equals(p.getIdusuario(), id)){
                filterPublicaiones.add(p.getContenido());
            }
        }
        return  filterPublicaiones;
    }
    @GetMapping("/allComentariosFromPublicacion/{id}")
    public List<Publicacion> getAllComentsFromPublish(@PathVariable Integer id){
        return  publicacionService.getAllComentsFromPublish(id);
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

    @GetMapping("/getAllPublication")
    public List<Publicacion> getUserPublications(){
        return  publicacionService.getUserPublications();
    }
    @GetMapping("/getUserComments/{id}")
    public List<Publicacion> getUserComments(@PathVariable Integer id){
        List<Publicacion> publicaciones = publicacionService.getAllPublicaciones();
        List<Publicacion> publicacionesFiltered = new ArrayList<Publicacion>();

        for(Publicacion p : publicaciones){
            if(Objects.equals(p.getIdusuario(), id)){
                if (p.getIdpublirefer() != null){
                    publicacionesFiltered.add(p);
                }

            }
        }
        return  publicacionesFiltered;
    }
    @DeleteMapping("/deleteById/{id}")
    public Boolean deletePublish(@PathVariable Integer id){
        return publicacionService.deleteUsuario(id);
    }

}
