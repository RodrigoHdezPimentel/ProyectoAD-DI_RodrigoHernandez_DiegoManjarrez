package dam.prueba.springPrueba.uploadingFiles.Storage;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);


    Path load(String filename);

    Resource loadAsResource(String filename);


}