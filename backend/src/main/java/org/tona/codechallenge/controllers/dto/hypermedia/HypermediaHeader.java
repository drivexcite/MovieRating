package org.tona.codechallenge.controllers.dto.hypermedia;

import lombok.Data;

public @Data class HypermediaHeader{
	private Links links;
	private int responseCode;	
}