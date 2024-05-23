package dam.prueba.spring_boot_foroex.controllers;


import dam.prueba.spring_boot_foroex.models.Tema;
import dam.prueba.spring_boot_foroex.servicies.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/tema")
public class TemaController {
    @Autowired
    private TemaService temaService;
    @GetMapping("/all")
    public List<Tema> getAllUTemas(){
        return temaService.getAllTemas();
    }
    @GetMapping("/id/{id}")
    public Optional<Tema> getTemaById(@PathVariable Integer id){
        return temaService.getTemaById(id);
    }
    @PostMapping("/save")
    public Tema saveTema(@RequestBody Tema tema){
        return temaService.saveTema(tema);
    }
    @GetMapping("/allName")
    public List<String> getAllTemasName(){
        List<Tema> temas = temaService.getAllTemas();
        List<String> namesTemas = new ArrayList<>();

        for(Tema t : temas){
            namesTemas.add(t.getTitulo());}

        return  namesTemas;
    }
    @GetMapping("/searchInName/{str}")
    public List<Tema> getsearchName(@PathVariable String str){
        List<Tema> temas = temaService.getAllTemas();
        List<Tema> temasFiltered = new ArrayList<Tema>();

        for(Tema p : temas){
            if(p.getTitulo().toLowerCase().contains(str.toLowerCase())){
                temasFiltered.add(p);
            }
        }
        return  temasFiltered;
    }
    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteTema(@PathVariable Integer id){
        return temaService.deleteTema(id);
    }


}
