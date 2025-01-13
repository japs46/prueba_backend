package com.backend.reactivo.app.domain.ports.out;

import com.backend.reactivo.app.domain.model.Producto;

import reactor.core.publisher.Mono;

public interface ProductoRepositoryPort {

	public Mono<Producto> save(Producto producto);
	
	public Mono<Void> delete(Long id);
	
	public Mono<Producto> findById(Long id);
}
