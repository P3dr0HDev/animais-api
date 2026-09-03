package br.com.senac.api.controller;

import br.com.senac.api.dtos.AnimalRequestDtos;
import br.com.senac.api.entities.Animal;
import br.com.senac.api.services.AnimalService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/listar")
    public ResponseEntity<List<Animal>> listar() {
        return ResponseEntity.ok(animalService.listar());
    }

    @PostMapping("/criar")
    public ResponseEntity<Animal> criar (@RequestBody AnimalRequestDtos animal) {
        return ResponseEntity.status(201).body(animalService.criar(animal));
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Animal> listarPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(animalService.listarPorId(id));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Animal> atualizar (@PathVariable Long id, @RequestBody AnimalRequestDtos animal){
        return ResponseEntity.ok(animalService.atualizar(id, animal));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            animalService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
