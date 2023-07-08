package exam2023.spring.cours.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exam2023.spring.cours.model.Auteur;
import exam2023.spring.cours.model.Genre;
import exam2023.spring.cours.model.Livre;
import exam2023.spring.cours.model.LivrePapier;
import exam2023.spring.cours.repository.AuteurRepository;
import exam2023.spring.cours.repository.GenreRepository;
import exam2023.spring.cours.repository.LivreRepository;

@RestController
@RequestMapping("/livres")
@CrossOrigin("*")
public class LivrePapierController {
	
	@Autowired
	GenreRepository genreRepository;
	@Autowired
	AuteurRepository auteurRepository;

    private final LivreRepository livreRepository;
    
   

    @Autowired
    public LivrePapierController(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    @GetMapping
    public ResponseEntity<List<Livre>> getAllLivresPapier() {
        List<Livre> livresPapier = livreRepository.findAllLivrePapier();
        return ResponseEntity.ok(livresPapier);
    }

    @PostMapping
    public ResponseEntity<Livre> addLivrePapier(@RequestBody LivrePapier livrePapier) {
    	Optional<Genre> resultat=genreRepository.findById(livrePapier.getGenre().getId());
    	Genre genre=null;
    
    	if (resultat.isPresent()) 
    		genre=resultat.get();
    	else
    		return ResponseEntity.notFound().eTag("Genre").build();
    	
    	List<Auteur> auteurs = new ArrayList<Auteur>();
    	
    	livrePapier.getAuteurs().forEach((aut)-> {
    		auteurs.add(auteurRepository.findById(aut.getId()).get());
    	});
    	livrePapier.setGenre(genre);
    	livrePapier.setAuteurs(auteurs);
 
        LivrePapier savedLivrePapier = livreRepository.save(livrePapier);
        return ResponseEntity.ok(savedLivrePapier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivrePapierById(@PathVariable("id") int id) {
        Livre livrePapier = livreRepository.findLivrePapierById(id);
        if (livrePapier == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livrePapier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivrePapier(@PathVariable("id") int id, @RequestBody LivrePapier livrePapier) {
        LivrePapier existingLivrePapier = livreRepository.findLivrePapierById(id);
        if (existingLivrePapier == null) {
            return ResponseEntity.notFound().build();
        }
        livrePapier.setId(id);
        LivrePapier updatedLivrePapier = livreRepository.save(livrePapier);
        return ResponseEntity.ok(updatedLivrePapier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivrePapier(@PathVariable("id") int id) {
        LivrePapier existingLivrePapier = livreRepository.findLivrePapierById(id);
        if (existingLivrePapier == null) {
            return ResponseEntity.notFound().build();
        }
        livreRepository.delete(existingLivrePapier);
        return ResponseEntity.noContent().build();
    }
}
