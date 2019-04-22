package org.tona.codechallenge.controllers.dto.hypermedia;

import java.util.Map;

import lombok.Data;

public @Data class Links {
	private Map<String, Object> links;
}