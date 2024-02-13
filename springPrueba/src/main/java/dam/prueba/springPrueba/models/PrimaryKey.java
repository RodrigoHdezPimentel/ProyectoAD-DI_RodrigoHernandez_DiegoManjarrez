package dam.prueba.springPrueba.models;

import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PrimaryKey {
    @EmbeddedId
    private Tema_Usuario pk;
}
