package exam2023.spring.cours.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import exam2023.spring.cours.model.Auteur;

public interface AuteurRepository extends JpaRepository<Auteur, Integer>{

}
