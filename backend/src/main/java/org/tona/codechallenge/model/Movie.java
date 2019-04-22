package org.tona.codechallenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Movie")
public @Data class Movie {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "MovieId")
	private int movieId;
	
	@Column(name = "Title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name="GenreId", nullable = false, updatable = true)
	private Genre genre;
	
	@Column(name = "Rating")
	private byte rating;	
}
