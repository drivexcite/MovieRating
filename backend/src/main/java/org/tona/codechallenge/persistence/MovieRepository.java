package org.tona.codechallenge.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tona.codechallenge.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	@Query(value = "select m from Movie m where m.title like %?1")
	Optional<Movie> getMovieByTitle(String title);
}
