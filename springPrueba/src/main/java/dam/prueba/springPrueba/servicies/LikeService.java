package dam.prueba.springPrueba.servicies;

import dam.prueba.springPrueba.models.Chat;
import dam.prueba.springPrueba.models.Like;
import dam.prueba.springPrueba.repositories.ChatRepository;
import dam.prueba.springPrueba.repositories.LikeRepository;
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


}