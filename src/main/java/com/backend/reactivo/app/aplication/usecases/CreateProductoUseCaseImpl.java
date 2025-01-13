package com.backend.reactivo.app.aplication.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.in.CreateProductoUseCase;
import com.backend.reactivo.app.domain.ports.in.RetrieveSucursalUseCase;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class CreateProductoUseCaseImpl implements CreateProductoUseCase{
	
	private final Logger LOG = LoggerFactory.getLogger(CreateProductoUseCaseImpl.class);
	
	private final ProductoRepositoryPort productoRepositoryPort;
	private final RetrieveSucursalUseCase retrieveSucursalUseCase;
	private final Validator validator;
	
	public CreateProductoUseCaseImpl(ProductoRepositoryPort productoRepositoryPort,
			RetrieveSucursalUseCase retrieveSucursalUseCase, Validator validator) {
		this.productoRepositoryPort = productoRepositoryPort;
		this.retrieveSucursalUseCase = retrieveSucursalUseCase;
		this.validator = validator;
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return retrieveSucursalUseCase.findById(producto.getIdSucursal())
	            .switchIfEmpty(Mono.error(new IllegalArgumentException("La sucursal con el ID proporcionado no existe")))
	            .then(Mono.justOrEmpty(producto))
	            .flatMap(this::validate)
	            .flatMap(productoRepositoryPort::save)
	            .doOnSuccess(savedProducto -> LOG.info("Producto guardado: " + savedProducto.toString()))
	            .doOnError(throwable -> LOG.error("Error al guardar el producto: " + throwable.getMessage()));
	}
	
	private Mono<Producto> validate(Producto producto) {
		BindException errors = new BindException(producto, Producto.class.getName());
		validator.validate(producto, errors);
		if (errors.hasErrors()) {
			return Mono.error(errors);
		}
		return Mono.just(producto);
	}

}
