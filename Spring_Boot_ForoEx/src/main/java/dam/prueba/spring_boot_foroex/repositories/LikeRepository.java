package dam.prueba.spring_boot_foroex.repositories;

import dam.prueba.spring_boot_foroex.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Like WHERE idPublicacion = ?1 and idUsuario = ?2")
    Integer removeLikeUser(Integer publishId, Integer userId);

}
