package com.backend.reactivo.app.domain.ports.in;

import com.backend.reactivo.app.domain.model.ProductoSucursal;

import reactor.core.publisher.Flux;

public interface RetrieveProductoUseCase {

	public Flux<ProductoSucursal> findProductoConMayorStockPorFranquicia(Long franquiciaId);
}
