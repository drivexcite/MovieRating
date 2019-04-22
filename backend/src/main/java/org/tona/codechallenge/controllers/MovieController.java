package org.tona.codechallenge.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import org.tona.codechallenge.controllers.dto.MovieDto;
import org.tona.codechallenge.controllers.dto.MovieUpdateDto;
import org.tona.codechallenge.controllers.exceptions.DuplicatedMovieException;
import org.tona.codechallenge.controllers.exceptions.GenreNotFoundException;
import org.tona.codechallenge.controllers.exceptions.MovieNotFoundException;
import org.tona.codechallenge.model.Movie;
import org.tona.codechallenge.persistence.GenreRepository;
import org.tona.codechallenge.persistence.MovieRepository;

@RestController
@RequestMapping(value="/api/movies")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@RequestMapping(value="/{movieId}", method=RequestMethod.GET)
	public ResponseEntity<Optional<MovieDto>> getMovie(@PathVariable int movieId) {
		// https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
		var movieDto = Optional
				.of(movieRepository.findById(movieId))
				.map(movie -> movie.isEmpty() ? null : modelMapper.map(movie.get(), MovieDto.class));
		
		// https://stackoverflow.com/a/44836511/2023191
		return Optional
				.ofNullable(movieDto)
				.map(movie -> movie.isEmpty() ? null : ResponseEntity.ok().body(movie))
				.orElseGet(() -> ResponseEntity.notFound().build());		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MovieDto>> getMovies() {
		var movies = movieRepository
				.findAll()
				.stream()
				.map(entity -> (MovieDto)modelMapper.map(entity, MovieDto.class))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(movies);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Optional<MovieDto>> createMovie(@RequestBody @Valid MovieDto movie, HttpServletRequest request) throws URISyntaxException, GenreNotFoundException, DuplicatedMovieException {
		
		// https://stackoverflow.com/questions/3686808/spring-3-requestmapping-get-path-value
		var path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		var genre = genreRepository.findByTitle(movie.getGenre().getTitle());
		if(genre.isEmpty())
			throw new GenreNotFoundException(movie.getGenre().getTitle());
		
		var existingMovie = movieRepository.getMovieByTitle(movie.getTitle());
		if(!existingMovie.isEmpty())
			throw new DuplicatedMovieException(movie.getTitle());
		
		var newMovie = new Movie();
		
		newMovie.setGenre(genre.get());
		newMovie.setTitle(movie.getTitle());
		newMovie.setRating(movie.getRating());
		
		movieRepository.save(newMovie);
		
		return ResponseEntity.created(new URI(String.format("%s/%s", path, newMovie.getMovieId()))).build();
	}	
	
	@RequestMapping(value = "/{movieId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateMovie(@RequestBody @Valid MovieUpdateDto movie, @PathVariable("movieId") int movieId) throws MovieNotFoundException, GenreNotFoundException {	     
		var existingMovie = movieRepository.findById(movieId);
		
		if(existingMovie.isEmpty())
			throw new MovieNotFoundException(movieId);
		
		if(movie.getRating() > 0)
			existingMovie.get().setRating(movie.getRating());
		
		if(movie.getGenre() != null) {
			var genre = genreRepository.findByTitle(movie.getGenre().getTitle());
			
			if(genre.isEmpty())
				throw new GenreNotFoundException(movie.getGenre().getTitle());
			
			existingMovie.get().setGenre(genre.get());
		}
		
		movieRepository.flush();
		
	    return ResponseEntity.ok().body(existingMovie);
	}
	
	@RequestMapping(value="/{movieId}", method=RequestMethod.DELETE)
	public ResponseEntity<Optional<MovieDto>> deleteMovie(@PathVariable int movieId) throws MovieNotFoundException {
		var existingMovie = movieRepository.findById(movieId);
		
		if(existingMovie.isEmpty())
			throw new MovieNotFoundException(movieId);
		
		movieRepository.delete(existingMovie.get());
		return ResponseEntity.ok().build();		
	}
}
