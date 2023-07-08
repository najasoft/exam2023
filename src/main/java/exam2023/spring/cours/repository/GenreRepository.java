package exam2023.spring.cours.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import exam2023.spring.cours.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer>{

}
