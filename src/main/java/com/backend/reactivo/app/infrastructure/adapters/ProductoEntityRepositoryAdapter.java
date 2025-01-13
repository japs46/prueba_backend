package com.backend.reactivo.app.infrastructure.adapters;

import org.springframework.stereotype.Repository;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.model.ProductoSucursal;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;
import com.backend.reactivo.app.infrastructure.entities.ProductoEntity;
import com.backend.reactivo.app.infrastructure.mappers.ProductoMapper;
import com.backend.reactivo.app.infrastructure.repositories.ProductoEntityRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductoEntityRepositoryAdapter implements ProductoRepositoryPort{
	
	private final ProductoEntityRepository productoEntityRepository;
	
	public ProductoEntityRepositoryAdapter(ProductoEntityRepository productoEntityRepository) {
		this.productoEntityRepository = productoEntityRepository;
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		ProductoEntity productoEntity = ProductoMapper.toEntity(producto);

		return productoEntityRepository.save(productoEntity)
				.map(ProductoMapper::toDomain);
	}

	@Override
	public Mono<Void> delete(Long id) {
		return productoEntityRepository.deleteById(id);
	}

	@Override
	public Mono<Producto> findById(Long id) {
		return productoEntityRepository.findById(id)
				.map(ProductoMapper::toDomain);
	}

	@Override
	public Flux<ProductoSucursal> findProductoConMayorStockPorFranquicia(Long franquiciaId) {
		return productoEntityRepository.findProductoConMayorStockPorFranquicia(franquiciaId);
	}

}
