package com.backend.reactivo.app.application.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.in.CreateFranquiciaUseCase;
import com.backend.reactivo.app.domain.ports.out.FranquiciaRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class CreateFranquiciaUseCaseImpl implements CreateFranquiciaUseCase{

	private final FranquiciaRepositoryPort franquiciaRepositoryPort;
	
	public CreateFranquiciaUseCaseImpl(FranquiciaRepositoryPort franquiciaRepositoryPort) {
		this.franquiciaRepositoryPort = franquiciaRepositoryPort;
	}

	@Override
	public Mono<Franquicia> save(Franquicia franquicia) {
		return franquiciaRepositoryPort.save(franquicia);
	}

}
