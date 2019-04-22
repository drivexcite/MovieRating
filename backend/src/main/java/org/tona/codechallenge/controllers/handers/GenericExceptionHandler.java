package org.tona.codechallenge.controllers.handers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tona.codechallenge.controllers.exceptions.DuplicatedMovieException;
import org.tona.codechallenge.controllers.exceptions.GenreNotFoundException;
import org.tona.codechallenge.controllers.exceptions.MovieNotFoundException;

@ControllerAdvice
public class GenericExceptionHandler {
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(GenreNotFoundException.class)
	public ResponseEntity handleGenreNotFoundException(GenreNotFoundException e) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(String.format("'%s' is not a valid Genre", e.getGenre()));
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(DuplicatedMovieException.class)
	public ResponseEntity handleDuplicatedMovieException(DuplicatedMovieException e) {
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(String.format("'%s' already exists", e.getTitle()));
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity handleMovieNotFoundException(MovieNotFoundException e) {
		var id = e.getTitle() != null 
				? String.format("[title] = %s", e.getTitle()) 
				: String.format("[movieId] = %s", e.getMovieId());
				
		var message = String.format("A movie with %s could not be found", id);
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(message);
	}
}
