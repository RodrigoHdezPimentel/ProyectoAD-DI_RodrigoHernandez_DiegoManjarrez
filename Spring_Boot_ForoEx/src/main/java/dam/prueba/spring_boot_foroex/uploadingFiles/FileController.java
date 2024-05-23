package dam.prueba.spring_boot_foroex.uploadingFiles;

import dam.prueba.spring_boot_foroex.uploadingFiles.Storage.StorageFileNotFoundException;
import dam.prueba.spring_boot_foroex.uploadingFiles.Storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/file")

public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        //Para obtenerlo en el formato imagen visual desde la URL
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
        //Para descargarlo del servidor
       /* return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + file.getFilename() + "\"").body(file);*/

    }
    @PostMapping("/saveListImages")
    public ResponseEntity<String> saveListImages(@RequestParam("images") MultipartFile[] files) {
        for (MultipartFile file : files) {
            storageService.storeImage(file);
        }
        return ResponseEntity.ok("Archivo subido correctamente");
        // Resource newFile = storageService.loadAsResource(fileName);
    }
    @PostMapping("/saveImagePC")
    public ResponseEntity<String> saveImage(@RequestParam("image") MultipartFile file) {
     storageService.storeImage(file);
        return ResponseEntity.ok("Archivo subido correctamente");
     // Resource newFile = storageService.loadAsResource(fileName);
    }
    @PostMapping("/saveImageApp")
    public ResponseEntity<String> saveImageApp(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok(storageService.saveImageApp(file));

    }

    @DeleteMapping("/deleteImage/{namePhoto}")
    public ResponseEntity<String> deleteImage (@PathVariable String namePhoto) throws IOException {
        Resource file = storageService.loadAsResource(namePhoto);
        if (file == null){
            return ResponseEntity.notFound().build();
        }
        file.getFile().delete();
        return ResponseEntity.ok("borrado exitosamente");
    }
    @GetMapping("/prueba")
    @ResponseBody
    public String prueba() throws IOException {

        File fi = new File("hola1.png");
        if(!fi.exists()){
            fi.createNewFile();
        }

        return "SIIIIIUUUUUUUUUUUUU";
        // Resource newFile = storageService.loadAsResource(fileName);
    }
       @PostMapping("/save")
    public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        return ResponseEntity.ok("SIUUUUUUUUUU");
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
