package org.tona.codechallenge.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;

public class DuplicatedMovieException extends Exception {
	private static final long serialVersionUID = 1L;
	public @Getter @Setter String title;	
	
	public DuplicatedMovieException() {
		this("");
	}
	
	public DuplicatedMovieException(String title) {
		this.title = title;
	}
}