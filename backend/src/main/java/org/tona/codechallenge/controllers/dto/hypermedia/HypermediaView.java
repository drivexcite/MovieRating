package org.tona.codechallenge.controllers.dto.hypermedia;

import lombok.Data;

public @Data class HypermediaView<T> {
	protected HypermediaHeader header;
	protected T data;
}
