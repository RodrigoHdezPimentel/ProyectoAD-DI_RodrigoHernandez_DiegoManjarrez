package dam.prueba.spring_boot_foroex.controllers;


import dam.prueba.spring_boot_foroex.models.Like;
import dam.prueba.spring_boot_foroex.servicies.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;
    @GetMapping("/all")
    public List<Like> getAllLikes(){
        return likeService.getAllLike();
    }
    @GetMapping("/id/{id}")
    public Optional<Like> getLikeById(@PathVariable Integer id){
        return likeService.getLikeById(id);
    }
    @GetMapping("/allUserLikes/{id}")
    public List<Like> getUserLikes(@PathVariable Integer id){
        List<Like> likes = likeService.getAllLike();
        List<Like> likesFiltered = new ArrayList<Like>();

        for(Like l : likes){
            if(l.getIdUsuario() == id){
                likesFiltered.add(l);
            }
        }
        return  likesFiltered;
    }
    @PostMapping("/save")
    public Like saveLike(@RequestBody Like like){
        return likeService.saveLike(like);
    }
    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteLike(@PathVariable Integer id){
        return likeService.deleteLike(id);
    }
    @DeleteMapping("/removeLikeUser/{idP}/{idU}")
    public Boolean removeLikeUser(@PathVariable Integer idP, @PathVariable Integer idU){
        return likeService.removeLikeUser(idP, idU);
    }

}
