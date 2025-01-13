package com.backend.reactivo.app.domain.ports.in;

import com.backend.reactivo.app.domain.model.Producto;

import reactor.core.publisher.Mono;

public interface UpdateProductoUseCase {

	public Mono<Producto> updateStock(Long id, Long stock);
}
