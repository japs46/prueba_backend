package com.backend.reactivo.app.aplication.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.app.domain.model.ProductoSucursal;
import com.backend.reactivo.app.domain.ports.in.RetrieveProductoUseCase;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;

import reactor.core.publisher.Flux;

@Component
public class RetrieveProductoUseCaseImpl implements RetrieveProductoUseCase{

	private final ProductoRepositoryPort productoRepositoryPort;
	
	public RetrieveProductoUseCaseImpl(ProductoRepositoryPort productoRepositoryPort) {
		this.productoRepositoryPort = productoRepositoryPort;
	}

	@Override
	public Flux<ProductoSucursal> findProductoConMayorStockPorFranquicia(Long franquiciaId) {
		return productoRepositoryPort.findProductoConMayorStockPorFranquicia(franquiciaId);
	}

}
