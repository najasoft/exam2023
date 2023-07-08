package exam2023.spring.cours.controller;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exam2023.spring.cours.model.Adherent;
import exam2023.spring.cours.model.Emprunt;
import exam2023.spring.cours.model.Livre;
import exam2023.spring.cours.repository.AdherentRepository;
import exam2023.spring.cours.repository.EmpruntRepository;

@RestController
@RequestMapping("/emprunts")
@CrossOrigin("*")
public class EmpruntController {

	private final EmpruntRepository empruntRepository;
	private final AdherentRepository adherentRepository;

	public EmpruntController(EmpruntRepository empruntRepository, AdherentRepository adherentRepository) {
		this.empruntRepository = empruntRepository;
		this.adherentRepository = adherentRepository;
	}
//question a 1,5
	@GetMapping("/adherent/{adherentId}")
	public ResponseEntity<List<Livre>> getLivres(@PathVariable("adherentId") int adherentId) {
		//Adherent adherent = adherentRepository.findById(adherentId).get();
		//Ou bien
		Adherent adherent=new Adherent();
		adherent.setId(adherentId);
		//Une
		List<Livre> livres = empruntRepository.findByAdherentEnCours(adherent).stream().map(em -> em.getLivre())
				.toList();
		return ResponseEntity.ok(livres);
	}
//question b 1,5
	@GetMapping("/adherent/{adherentId}/dates")
	public ResponseEntity<List<Emprunt>> getEmpruntsByAdherentAndDates(@PathVariable("adherentId") int adherentId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);
		Adherent adherent = this.adherentRepository.findById(adherentId).get();
		List<Emprunt> emprunts = empruntRepository.findEmpruntsByAdherentAndDates(adherent, start, end);
		return ResponseEntity.ok(emprunts);
	}
// question c 3
	@GetMapping("/adherents-retard")
	public ResponseEntity<List<Adherent>> getAdherentsWithLivresEnRetard() {
		LocalDate today = LocalDate.now();
		List<Emprunt> emprunts = empruntRepository.findEmpruntsByEtat("EnCours");
		List<Emprunt> empruntsRetard = emprunts.stream()
				.filter(emp -> ChronoUnit.DAYS.between(emp.getDateEmprunt(), today) > 15).toList();
		List<Adherent> adherents = empruntsRetard.stream().map(emp -> emp.getAdherent()).toList();
		return ResponseEntity.ok(adherents);
	}
// question d 6
	@PostMapping
	public ResponseEntity<Emprunt> addEmprunt(@RequestBody Emprunt emprunt) {
		try {
			int nbEmprunts = empruntRepository.findByAdherentEnCours(emprunt.getAdherent()).size();
			if (nbEmprunts > 3)
				return ResponseEntity.badRequest().build();
			emprunt.setDateEmprunt(LocalDate.now());
			emprunt.setEtat("EnCours");
			Emprunt newEmprunt = empruntRepository.save(emprunt);
			return ResponseEntity.status(HttpStatus.CREATED).body(newEmprunt);
		} catch (Exception exp) {
			return ResponseEntity.badRequest().build();
		}
	}
}
