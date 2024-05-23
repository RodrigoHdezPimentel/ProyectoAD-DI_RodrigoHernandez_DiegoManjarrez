package dam.prueba.spring_boot_foroex.uploadingFiles.Storage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {

        if(properties.getLocation().trim().isEmpty()){
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get((Objects.requireNonNull(file.getOriginalFilename()))))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
    @Override
    public void storeImage(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String fileName = UUID.randomUUID().toString();
//        Para ponerle un nombre únicopara que n haya archivos con los nombre iguales o que se sobreescriba
            String fileExtension = getFileExtension(file);

//        guardamos el archivo en la ruta
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(fileName + fileExtension))
                    .normalize().toAbsolutePath();

//        Para verificar la ubicacion del archivo
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }



    @Override
    public String saveImageApp(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String fileName = UUID.randomUUID().toString();
//        Para ponerle un nombre únicopara que n haya archivos con los nombre iguales o que se sobreescriba
            String fileExtension = getFileExtension(file);

//        guardamos el archivo en la ruta
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(fileName + fileExtension))
                    .normalize().toAbsolutePath();

//        Para verificar la ubicacion del archivo
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return fileName+fileExtension;

        }catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }

    }


    private static String getFileExtension(MultipartFile file) {
        String fileOriginalName = file.getOriginalFilename();

        if (!(
                fileOriginalName.endsWith(".jpeg") ||
                fileOriginalName.endsWith(".jpg") ||
                fileOriginalName.endsWith(".png")
        )) {
            throw new StorageException("Only JPG, JPEG, PNG files are allowed!");
        }

        //Le Ponemos el nombre del archivo con un identificadro unico usando el UUID
        return fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
    }



    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());


            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}

