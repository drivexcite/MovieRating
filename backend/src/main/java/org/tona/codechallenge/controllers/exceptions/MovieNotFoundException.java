package org.tona.codechallenge.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;

public class MovieNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public @Getter @Setter String title;	
	public @Getter @Setter int movieId;
	
	public MovieNotFoundException() {
	}
	
	public MovieNotFoundException(String title) {
		this.title = title;
	}
	
	public MovieNotFoundException(int movieId) {
		this.movieId = movieId;
	}
}
