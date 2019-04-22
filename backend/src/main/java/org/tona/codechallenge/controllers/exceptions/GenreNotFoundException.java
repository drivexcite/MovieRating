package org.tona.codechallenge.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;

public class GenreNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public @Getter @Setter String genre;	
	
	public GenreNotFoundException() {
		this("");
	}
	
	public GenreNotFoundException(String genre) {
		this.genre = genre;
	}
}
