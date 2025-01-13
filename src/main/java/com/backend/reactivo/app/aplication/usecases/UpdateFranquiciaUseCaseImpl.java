package com.backend.reactivo.app.aplication.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.in.UpdateFranquiciaUseCase;
import com.backend.reactivo.app.domain.ports.out.FranquiciaRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class UpdateFranquiciaUseCaseImpl implements UpdateFranquiciaUseCase{
	
	private final FranquiciaRepositoryPort franquiciaRepositoryPort;
	
	public UpdateFranquiciaUseCaseImpl(FranquiciaRepositoryPort franquiciaRepositoryPort) {
		this.franquiciaRepositoryPort = franquiciaRepositoryPort;
	}

	@Override
	public Mono<Franquicia> updateNombre(Long id, String nombre) {
		return franquiciaRepositoryPort.findById(id)
	            .switchIfEmpty(Mono.error(new IllegalArgumentException("Franquicia no encontrado con id: " + id)))
	            .flatMap(franquicia -> {
	                Franquicia updateFranquicia = new Franquicia(franquicia.getId(),nombre);
	                return franquiciaRepositoryPort.save(updateFranquicia);
	            });
	}

}
