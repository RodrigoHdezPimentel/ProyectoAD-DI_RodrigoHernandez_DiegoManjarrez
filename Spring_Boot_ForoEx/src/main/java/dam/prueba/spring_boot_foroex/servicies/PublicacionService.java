package dam.prueba.spring_boot_foroex.servicies;

import dam.prueba.spring_boot_foroex.models.Publicacion;
import dam.prueba.spring_boot_foroex.repositories.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;

    public List<Publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();
    }

    public List<Publicacion> getAllPublicacionesByUser(Integer id) {
        return publicacionRepository.findAll();
    }

    public Optional<Publicacion> getPublicacionById(Integer id) {
        return publicacionRepository.findById(id);
    }

    public Publicacion savePublicacion(Publicacion publicacion){
        return publicacionRepository.save(publicacion);
    }
    public Boolean deleteUsuario(Integer id){
        publicacionRepository.deleteById(id);
        return publicacionRepository.findById(id).isEmpty();
    }
    public List<Publicacion> getUserPublications(){
        return publicacionRepository.getUserPublications();
    }
    public List<Publicacion> getAllComentsFromPublish(Integer id){
        return publicacionRepository.getAllComentsFromPublish(id);
    }
    public List<Publicacion> getPublishTrending(){
        return publicacionRepository.getPublishTrending();
    }


}