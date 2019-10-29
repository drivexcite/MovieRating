package org.tona.codechallenge.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/health")
public class HealthController {

	@RequestMapping(method = RequestMethod.GET)
	public String getGenres() {
		return "yes";
	}
}