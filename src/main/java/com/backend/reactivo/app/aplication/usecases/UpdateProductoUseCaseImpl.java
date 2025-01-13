package com.backend.reactivo.app.aplication.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.in.UpdateProductoUseCase;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class UpdateProductoUseCaseImpl implements UpdateProductoUseCase{

	private final ProductoRepositoryPort productoRepositoryPort;
	
	public UpdateProductoUseCaseImpl(ProductoRepositoryPort productoRepositoryPort) {
		this.productoRepositoryPort = productoRepositoryPort;
	}

	@Override
	public Mono<Producto> updateStock(Long id, Long stock) {
		return productoRepositoryPort.findById(id)
	            .switchIfEmpty(Mono.error(new IllegalArgumentException("Producto no encontrado con id: " + id)))
	            .flatMap(producto -> {
	                Producto updatedProducto = new Producto(
	                        producto.getId(),
	                        producto.getNombre(),
	                        stock,
	                        producto.getIdSucursal()
	                );
	                return productoRepositoryPort.save(updatedProducto);
	            });
	}

}
