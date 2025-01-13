package com.backend.reactivo.app.aplication.services.impl;

import org.springframework.stereotype.Service;

import com.backend.reactivo.app.aplication.services.ProductoService;
import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.in.CreateProductoUseCase;

import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	private final CreateProductoUseCase createProductoUseCase;
	
	public ProductoServiceImpl(CreateProductoUseCase createProductoUseCase) {
		this.createProductoUseCase = createProductoUseCase;
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return createProductoUseCase.save(producto);
	}

}
