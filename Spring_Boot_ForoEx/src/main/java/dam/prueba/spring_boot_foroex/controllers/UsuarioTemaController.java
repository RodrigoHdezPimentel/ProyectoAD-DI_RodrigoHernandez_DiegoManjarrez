package dam.prueba.spring_boot_foroex.controllers;

import dam.prueba.spring_boot_foroex.models.UsuarioTema;
import dam.prueba.spring_boot_foroex.servicies.UsuarioTemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarioTema")
public class UsuarioTemaController {
    @Autowired
    private UsuarioTemaService usuarioTemaService;
    @GetMapping("/all")
    public List<UsuarioTema> getAllUsuarioTema(){
        return usuarioTemaService.getAllUsuarioTema();
    }

    @GetMapping("/getAllUserTema/{id}")
    public List<UsuarioTema> getAllTemaFromId(@PathVariable Integer id){
        return usuarioTemaService.getAllTemaFromId(id);
    }
    @PostMapping("/save")
    public UsuarioTema saveUserTema(@RequestBody UsuarioTema userTema){
        return usuarioTemaService.saveUserTema(userTema);
    }
    @DeleteMapping("/removeTemaUser/{idT}/{idU}")
    public Boolean removeTemaUser(@PathVariable Integer idT, @PathVariable Integer idU){
        return usuarioTemaService.removeTemaUser(idT, idU);
    }

}
