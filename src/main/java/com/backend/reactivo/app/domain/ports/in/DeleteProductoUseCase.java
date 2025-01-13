package com.backend.reactivo.app.domain.ports.in;

import reactor.core.publisher.Mono;

public interface DeleteProductoUseCase {

	public Mono<Void> delete(Long id);
}
