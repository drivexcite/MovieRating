package org.tona.codechallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tona.codechallenge.model.Genre;
import org.tona.codechallenge.persistence.GenreRepository;

@RestController
@RequestMapping(value = "/api/genres")
public class GenreController {

	@Autowired
	private GenreRepository genreRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<Genre> getGenres() {
		return genreRepository.findAll();
	}
}
