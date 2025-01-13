package com.backend.reactivo.app.domain.ports.out;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.model.ProductoSucursal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepositoryPort {

	public Mono<Producto> save(Producto producto);
	
	public Mono<Void> delete(Long id);
	
	public Mono<Producto> findById(Long id);
	
	public Flux<ProductoSucursal> findProductoConMayorStockPorFranquicia(Long franquiciaId);
	
}
