package com.backend.reactivo.app.aplication.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.in.RetrieveFranquiciaUseCase;
import com.backend.reactivo.app.domain.ports.out.FranquiciaRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class RetrieveFranquiciaUseCaseImpl implements RetrieveFranquiciaUseCase{
	
	private final FranquiciaRepositoryPort franquiciaRepositoryPort;

	public RetrieveFranquiciaUseCaseImpl(FranquiciaRepositoryPort franquiciaRepositoryPort) {
		this.franquiciaRepositoryPort = franquiciaRepositoryPort;
	}

	@Override
	public Mono<Franquicia> findById(Long id) {
		return franquiciaRepositoryPort.findById(id);
	}

}
