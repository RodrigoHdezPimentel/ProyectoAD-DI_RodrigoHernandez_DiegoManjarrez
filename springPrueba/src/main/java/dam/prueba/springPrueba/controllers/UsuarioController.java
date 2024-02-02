package dam.prueba.springPrueba.controllers;


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
@RequestMapping("/Usuario")
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
        List<Usuario> productsFiltered = new ArrayList<Usuario>();

        for(Usuario p : usuarios){
            if(p.getName().toLowerCase().contains(str.toLowerCase())){
                productsFiltered.add(p);
            }
        }
        return  productsFiltered;
    }
    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteProduct(@PathVariable Integer id){
        return usuarioService.deleteUsuario(id);
    }


}
