package dam.prueba.springPrueba.uploadingFiles;

import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.uploadingFiles.Storage.StorageFileNotFoundException;
import dam.prueba.springPrueba.uploadingFiles.Storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<String> saveListImages(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            storageService.storeImage(file);
        }
        return ResponseEntity.ok("Archivo subido correctamente");
        // Resource newFile = storageService.loadAsResource(fileName);
    }
    @PostMapping("/saveImage")
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile file) {
     storageService.storeImage(file);
        return ResponseEntity.ok("Archivo subido correctamente");
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
