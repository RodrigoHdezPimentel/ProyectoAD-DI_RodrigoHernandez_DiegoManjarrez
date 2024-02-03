package dam.prueba.springPrueba.controllers;


import dam.prueba.springPrueba.models.Like;
import dam.prueba.springPrueba.servicies.LikeService;
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
    public List<Like> getAllULikes(){
        return likeService.getAllLike();
    }
    @GetMapping("/id/{id}")
    public Optional<Like> getLikeById(@PathVariable Integer id){
        return likeService.getLikeById(id);
    }
    @GetMapping("/allUserLikes/{id}")
    public List<Like> getsearchName(@PathVariable Integer id){
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


}
