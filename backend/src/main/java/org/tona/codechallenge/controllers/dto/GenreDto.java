package org.tona.codechallenge.controllers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

public @Data class GenreDto {
	private int genreId;
	
	@NotNull
	@Size(min=1, max=200)
	private String title;
}
