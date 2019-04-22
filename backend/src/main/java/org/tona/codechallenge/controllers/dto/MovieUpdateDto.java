package org.tona.codechallenge.controllers.dto;

import javax.validation.Valid;
import org.hibernate.validator.constraints.Range;
import lombok.Data;

public @Data class MovieUpdateDto {	
	@Valid
	private GenreDto genre;
	
	@Range(min=0, max=5)
	private byte rating;
}