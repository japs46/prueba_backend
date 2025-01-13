package com.backend.reactivo.app.aplication.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.CreateSucursalUseCase;
import com.backend.reactivo.app.domain.ports.in.RetrieveFranquiciaUseCase;
import com.backend.reactivo.app.domain.ports.out.SucursalRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class CreateSucursalUseCaseImpl implements CreateSucursalUseCase{
	
	private final Logger LOG = LoggerFactory.getLogger(CreateSucursalUseCaseImpl.class);
	
	private final SucursalRepositoryPort sucursalRepositoryPort;
	private final RetrieveFranquiciaUseCase retrieveFranquiciaUseCase;
	private final Validator validator;
	
	public CreateSucursalUseCaseImpl(SucursalRepositoryPort sucursalRepositoryPort,
			RetrieveFranquiciaUseCase retrieveFranquiciaUseCase, Validator validator) {
		this.sucursalRepositoryPort = sucursalRepositoryPort;
		this.retrieveFranquiciaUseCase = retrieveFranquiciaUseCase;
		this.validator = validator;
	}

	@Override
	public Mono<Sucursal> save(Sucursal sucursal) {
		
		return retrieveFranquiciaUseCase.findById(sucursal.getIdFranquicia())
	            .switchIfEmpty(Mono.error(new IllegalArgumentException("La franquicia con el ID proporcionado no existe")))
	            .then(Mono.justOrEmpty(sucursal))
	            .flatMap(this::validate)
	            .flatMap(sucursalRepositoryPort::save)
	            .doOnSuccess(savedSucursal -> LOG.info("Sucursal guardada: " + savedSucursal.toString()))
	            .doOnError(throwable -> LOG.error("Error al guardar la sucursal: " + throwable.getMessage()));
	}
	
	private Mono<Sucursal> validate(Sucursal sucursal) {
		BindException errors = new BindException(sucursal, Sucursal.class.getName());
		validator.validate(sucursal, errors);
		if (errors.hasErrors()) {
			return Mono.error(errors);
		}
		return Mono.just(sucursal);
	}

}
