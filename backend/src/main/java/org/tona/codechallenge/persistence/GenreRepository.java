package org.tona.codechallenge.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tona.codechallenge.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
	@Query(value = "select g from Genre g where g.title like %?1")
	Optional<Genre> findByTitle(String title);
}
