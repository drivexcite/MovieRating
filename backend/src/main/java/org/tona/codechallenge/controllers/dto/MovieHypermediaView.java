package org.tona.codechallenge.controllers.dto;

import java.util.HashMap;

import org.tona.codechallenge.controllers.dto.hypermedia.HypermediaHeader;
import org.tona.codechallenge.controllers.dto.hypermedia.HypermediaView;
import org.tona.codechallenge.controllers.dto.hypermedia.Links;

public class MovieHypermediaView extends HypermediaView<MovieDto> {
		
	public MovieHypermediaView(String controllerBaseUrl, MovieDto movie) {
		data = movie;
		
		var links = new Links();		
		links.setLinks(new HashMap<String, Object>());
		links.getLinks().put("self", String.format("%s/%s", controllerBaseUrl, movie.getMovieId()));
		
		header = new HypermediaHeader();				
		header.setResponseCode(200);			
		header.setLinks(links);
	}
	
	public MovieHypermediaView(String controllerBaseUrl, int movieId, int statusCode) {
		var links = new Links();		
		links.setLinks(new HashMap<String, Object>());
		links.getLinks().put("self", String.format("%s/%s", controllerBaseUrl, movieId));
		
		header = new HypermediaHeader();				
		header.setResponseCode(statusCode);			
		header.setLinks(links);
	}
}
