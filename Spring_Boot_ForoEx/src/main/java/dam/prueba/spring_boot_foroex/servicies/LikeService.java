package dam.prueba.spring_boot_foroex.servicies;

import dam.prueba.spring_boot_foroex.models.Like;
import dam.prueba.spring_boot_foroex.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public List<Like> getAllLike() {
        return likeRepository.findAll();
    }

    public Optional<Like> getLikeById(Integer id) {
        return likeRepository.findById(id);
    }

    public Like saveLike(Like like){
        return likeRepository.save(like);
    }
    public Boolean deleteLike(Integer id){
        likeRepository.deleteById(id);
        return likeRepository.findById(id).isEmpty();
    }
    public Boolean  removeLikeUser(Integer idP, Integer idU){
         return likeRepository.removeLikeUser(idP, idU) > 0 ;
    }

}