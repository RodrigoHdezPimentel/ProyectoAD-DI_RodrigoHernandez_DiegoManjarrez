package dam.prueba.springPrueba.controllers;


import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.servicies.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/all")
    public List<Usuario> getAllUsuarios(){
        return usuarioService.getAllUsuarios();
    }
    @GetMapping("/id/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Integer id){
        return usuarioService.getUsuarioById(id);
    }
    @PostMapping("/save")
    public Usuario saveUsuario(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }
    @PutMapping("/update")
    public Usuario updateUsuario(@RequestBody Usuario usuario){
        return usuarioService.updateUsuario(usuario);
    }

    @GetMapping("/allName")
    public List<String> getAllUsuariosName(){
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        List<String> namesProducts = new ArrayList<String>();

        for(Usuario p : usuarios){
            namesProducts.add(p.getName());}

        return  namesProducts;
    }
    @GetMapping("/searchInName/{str}")
    public List<Usuario> getsearchName(@PathVariable String str){
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        List<Usuario> usuariosFiltered = new ArrayList<Usuario>();

        for(Usuario p : usuarios){
            if(p.getName().toLowerCase().contains(str.toLowerCase())){
                usuariosFiltered.add(p);
            }
        }
        return  usuariosFiltered;
    }
    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteUsuario(@PathVariable Integer id){
        return usuarioService.deleteUsuario(id);
    }


    @GetMapping("/searchSelect/{str}")
    public List<String> getByName(@PathVariable String str){

        return  usuarioService.getByName(str);
    }
    @GetMapping("/getUserPublication/{id}")
    public List<Publicacion> getUserPublicacion(@PathVariable Integer id){
//        ArrayList<Publicacion> listaPublicaciones = (ArrayList<Publicacion>) usuarioService.getUserPublicacion(id);
//        ArrayList<Publicacion> listaPublicacionesFiltered = new ArrayList<>();
//        for(Publicacion l: listaPublicaciones){
//            if (l.getIdpublirefer() == null){
//                listaPublicacionesFiltered.add(l);
//            }
//        }
//        return listaPublicacionesFiltered;
        return usuarioService.getUserPublicacion(id);
    }
    /*@GetMapping("/getUserLikes/{id}")
    public List<Publicacion> getUserLike(@PathVariable Integer id){
        return  usuarioService.getUserLike(id);
    }*/

    @GetMapping("/getUserPublicacionFromTema/{id}")
    public List<Publicacion> getUserPublicacionFromTema(@PathVariable Integer id){
        return  usuarioService.getUserPublicacionFromTema(id);
    }

}
