package com.backend.reactivo.app.aplication.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.app.domain.ports.in.DeleteProductoUseCase;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class DeleteProductoUseCaseImpl implements DeleteProductoUseCase{
	
	private final ProductoRepositoryPort productoRepositoryPort;
	
	public DeleteProductoUseCaseImpl(ProductoRepositoryPort productoRepositoryPort) {
		this.productoRepositoryPort = productoRepositoryPort;
	}

	@Override
	public Mono<Void> delete(Long id) {
		return productoRepositoryPort.findById(id)
				.switchIfEmpty(Mono.error(new IllegalArgumentException("Producto no encontrado con id: " + id)))
                .flatMap(producto -> productoRepositoryPort.delete(producto.getId()));
	}

}
