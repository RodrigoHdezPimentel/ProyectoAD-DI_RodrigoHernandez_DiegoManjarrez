package dam.prueba.springPrueba.controllers;


import dam.prueba.springPrueba.models.Publicacion;
import dam.prueba.springPrueba.models.Usuario;
import dam.prueba.springPrueba.servicies.UsuarioService;
import dam.prueba.springPrueba.uploadingFiles.Storage.StorageException;
import dam.prueba.springPrueba.uploadingFiles.Storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteUsuario(@PathVariable Integer id){
        return usuarioService.deleteUsuario(id);
    }

    //-----------------------------COLOCAR EL NOMBRE DE LA FOTO A TODOS LOS USER EN LA DB------------------------------------------------//
    @PutMapping("/fotosUsuarios/{idU}/{image}")
    public void fotosUsuarios(@PathVariable Integer idU,@PathVariable String image){
      usuarioService.fotosUsuarios(idU, image);
    }
    @GetMapping("/fotosUsuarios/actualizar")
    public String colocarFotos(){
        int numId=0;

        StorageProperties nombreCarpeta = new StorageProperties();
        Path rootLocation = Paths.get(nombreCarpeta.getLocation());

        File Carpeta = new File(String.valueOf(rootLocation));

        if(Carpeta.isDirectory()){
            String nombreImagenes[] = Carpeta.list();

            for(;numId < nombreImagenes.length;numId++){
                fotosUsuarios(numId+1,nombreImagenes[numId]);
            }

        }
            return "Cantidad de usuarios con foto: " + numId;
    }

    //-------------------------------------------------------------------------------------------------------//


@GetMapping("/searchSelect/{str}")
    public List<String> getByName(@PathVariable String str){

        return  usuarioService.getByName(str);
    }
    @GetMapping("/getUserPublication/{id}")
    public List<Publicacion> getUserPublicacion(@PathVariable Integer id){

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
    @GetMapping("/getUserPublicacionFromLike/{id}")
    public List<Publicacion> getUserPublicacionFromLike(@PathVariable Integer id){
        return  usuarioService.getUserPublicacionFromLike(id);
    }
    @GetMapping("/getUserRegister/{name}/{pass}")
    public Optional<Usuario> getUserRegister(@PathVariable String name, @PathVariable String pass){ return  usuarioService.getUserRegister(name, pass);}

}
