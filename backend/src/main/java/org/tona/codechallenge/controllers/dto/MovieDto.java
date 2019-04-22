package org.tona.codechallenge.controllers.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

public @Data class MovieDto {
	private int movieId;
	
	@NotNull
	@Size(min=1, max=200)
	private String title;
	
	@Valid
	private GenreDto genre;
	
	@Range(min=1, max=5)
	private byte rating;
}
