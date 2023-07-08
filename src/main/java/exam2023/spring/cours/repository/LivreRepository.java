package exam2023.spring.cours.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import exam2023.spring.cours.model.Livre;
import exam2023.spring.cours.model.LivrePapier;

public interface LivreRepository extends JpaRepository<Livre, Integer> {
	@Query("SELECT livre FROM LivrePapier livre")
	List<Livre> findAllLivrePapier();

	LivrePapier findLivrePapierById(int id);

}
