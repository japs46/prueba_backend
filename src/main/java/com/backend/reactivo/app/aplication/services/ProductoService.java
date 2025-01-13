package com.backend.reactivo.app.aplication.services;

import com.backend.reactivo.app.domain.model.Producto;

import reactor.core.publisher.Mono;

public interface ProductoService {

	public Mono<Producto> save(Producto producto);
	
	public Mono<Void> delete(Long id);
	
	public Mono<Producto> updateStock(Long id, Long stock);
}
