package com.backend.reactivo.app.aplication.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.in.CreateFranquiciaUseCase;
import com.backend.reactivo.app.domain.ports.out.FranquiciaRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class CreateFranquiciaUseCaseImpl implements CreateFranquiciaUseCase {

	private final Logger LOG = LoggerFactory.getLogger(CreateFranquiciaUseCaseImpl.class);

	private final FranquiciaRepositoryPort franquiciaRepositoryPort;
	private final Validator validator;

	public CreateFranquiciaUseCaseImpl(FranquiciaRepositoryPort franquiciaRepositoryPort, Validator validator) {
		this.franquiciaRepositoryPort = franquiciaRepositoryPort;
		this.validator = validator;
	}

	@Override
	public Mono<Franquicia> save(Franquicia franquicia) {

		return Mono.justOrEmpty(franquicia)
				.flatMap(f -> validate(f))
				.flatMap(f -> franquiciaRepositoryPort.save(f)
				.doOnSuccess(savedFranquicia -> {
					LOG.info("Franquicia guardada: " + savedFranquicia.toString());
				}).doOnError(throwable -> {
					LOG.error("Error al guardar la franquicia: " + throwable.getMessage());
				}));

	}

	private Mono<Franquicia> validate(Franquicia franquicia) {
		BindException errors = new BindException(franquicia, Franquicia.class.getName());
		validator.validate(franquicia, errors);
		if (errors.hasErrors()) {
			return Mono.error(errors);
		}
		return Mono.just(franquicia);
	}
}
