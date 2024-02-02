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
@RequestMapping("/Publicacion")
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
    public Publicacion saveUsuario(@RequestBody Publicacion publicacion){
        return publicacionService.savePublicacion(publicacion);
    }
    @GetMapping("/allContent")
    public List<String> getAllUsuariosName(){
        List<Publicacion> publicaciones = publicacionService.getAllPublicaciones();
        List<String> contenPublicaiones = new ArrayList<String>();

        for(Publicacion p : publicaciones){
            contenPublicaiones.add(p.getContenido());}

        return  contenPublicaiones;
    }
    @GetMapping("/searchInName/{str}")
    public List<Publicacion> getsearchName(@PathVariable String str){
        List<Publicacion> publicaciones = publicacionService.getAllPublicaciones();
        List<Publicacion> productsFiltered = new ArrayList<Publicacion>();

        for(Publicacion p : publicaciones){
            if(p.getContenido().toLowerCase().contains(str.toLowerCase())){
                productsFiltered.add(p);
            }
        }
        return  productsFiltered;
    }
    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteProduct(@PathVariable Integer id){
        return publicacionService.deleteUsuario(id);
    }


}
