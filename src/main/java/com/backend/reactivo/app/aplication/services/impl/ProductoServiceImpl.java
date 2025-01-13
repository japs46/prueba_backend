package com.backend.reactivo.app.aplication.services.impl;

import org.springframework.stereotype.Service;

import com.backend.reactivo.app.aplication.services.ProductoService;
import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.in.CreateProductoUseCase;
import com.backend.reactivo.app.domain.ports.in.DeleteProductoUseCase;
import com.backend.reactivo.app.domain.ports.in.UpdateProductoUseCase;

import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	private final CreateProductoUseCase createProductoUseCase;
	private final DeleteProductoUseCase deleteProductoUseCase;
	private final UpdateProductoUseCase updateProductoUseCase;
	
	public ProductoServiceImpl(CreateProductoUseCase createProductoUseCase, DeleteProductoUseCase deleteProductoUseCase,
			UpdateProductoUseCase updateProductoUseCase) {
		this.createProductoUseCase = createProductoUseCase;
		this.deleteProductoUseCase = deleteProductoUseCase;
		this.updateProductoUseCase = updateProductoUseCase;
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return createProductoUseCase.save(producto);
	}

	@Override
	public Mono<Void> delete(Long id) {
		return deleteProductoUseCase.delete(id);
	}

	@Override
	public Mono<Producto> updateStock(Long id, Long stock) {
		return updateProductoUseCase.updateStock(id, stock);
	}

}
