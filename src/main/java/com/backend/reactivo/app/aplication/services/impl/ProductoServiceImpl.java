package com.backend.reactivo.app.aplication.services.impl;

import org.springframework.stereotype.Service;

import com.backend.reactivo.app.aplication.services.ProductoService;
import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.in.CreateProductoUseCase;
import com.backend.reactivo.app.domain.ports.in.DeleteProductoUseCase;

import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	private final CreateProductoUseCase createProductoUseCase;
	private final DeleteProductoUseCase deleteProductoUseCase;
	
	public ProductoServiceImpl(CreateProductoUseCase createProductoUseCase,
			DeleteProductoUseCase deleteProductoUseCase) {
		this.createProductoUseCase = createProductoUseCase;
		this.deleteProductoUseCase = deleteProductoUseCase;
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return createProductoUseCase.save(producto);
	}

	@Override
	public Mono<Void> delete(Long id) {
		return deleteProductoUseCase.delete(id);
	}

}
