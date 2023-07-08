package exam2023.spring.cours.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exam2023.spring.cours.model.Auteur;
import exam2023.spring.cours.repository.AuteurRepository;

@RestController
@RequestMapping("/auteurs")
public class AuteurController {

    private final AuteurRepository auteurRepository;

    @Autowired
    public AuteurController(AuteurRepository auteurRepository) {
        this.auteurRepository = auteurRepository;
    }

    @GetMapping
    public ResponseEntity<List<Auteur>> getAllAuteurs() {
        List<Auteur> auteurs = auteurRepository.findAll();
        return ResponseEntity.ok(auteurs);
    }

    @PostMapping
    public ResponseEntity<Auteur> addAuteur(@RequestBody Auteur auteur) {
        Auteur savedAuteur = auteurRepository.save(auteur);
        return ResponseEntity.ok(savedAuteur);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auteur> getAuteurById(@PathVariable("id") int id) {
        Auteur auteur = auteurRepository.findById(id).orElse(null);
        if (auteur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auteur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auteur> updateAuteur(@PathVariable("id") int id, @RequestBody Auteur auteur) {
        Auteur existingAuteur = auteurRepository.findById(id).orElse(null);
        if (existingAuteur == null) {
            return ResponseEntity.notFound().build();
        }
        auteur.setId(id);
        Auteur updatedAuteur = auteurRepository.save(auteur);
        return ResponseEntity.ok(updatedAuteur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable("id") int id) {
        Auteur existingAuteur = auteurRepository.findById(id).orElse(null);
        if (existingAuteur == null) {
            return ResponseEntity.notFound().build();
        }
        auteurRepository.delete(existingAuteur);
        return ResponseEntity.noContent().build();
    }
}
