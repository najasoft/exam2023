package exam2023.spring.cours.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import exam2023.spring.cours.model.Adherent;
import exam2023.spring.cours.model.Emprunt;
import exam2023.spring.cours.model.Livre;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Integer> {
	@Query("SELECT e FROM Emprunt e WHERE e.adherent = :adherent AND e.etat = 'EnCours'")
	List<Emprunt> findByAdherentEnCours(@Param("adherent") Adherent adherent);

	List<Emprunt> findByLivre(Livre livre);

	//List<Emprunt> findByAdherentAndEtat(Adherent adherent, String etat);

	@Query("SELECT e FROM Emprunt e WHERE e.adherent = :adherent AND e.dateEmprunt >= :startDate AND e.dateEmprunt <= :endDate")
	List<Emprunt> findEmpruntsByAdherentAndDates(@Param("adherent") Adherent adherent,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query("SELECT e FROM Emprunt e WHERE e.etat = :etat")
	List<Emprunt> findEmpruntsByEtat(@Param("etat") String etat);
}
