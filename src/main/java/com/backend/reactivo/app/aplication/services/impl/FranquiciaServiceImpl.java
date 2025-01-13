package com.backend.reactivo.app.aplication.services.impl;

import org.springframework.stereotype.Service;

import com.backend.reactivo.app.aplication.services.FranquiciaService;
import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.in.CreateFranquiciaUseCase;
import com.backend.reactivo.app.domain.ports.in.UpdateFranquiciaUseCase;

import reactor.core.publisher.Mono;

@Service
public class FranquiciaServiceImpl implements FranquiciaService{
	
	private final CreateFranquiciaUseCase createFranquiciaUseCase;
	private final UpdateFranquiciaUseCase updateFranquiciaUseCase;

	public FranquiciaServiceImpl(CreateFranquiciaUseCase createFranquiciaUseCase,
			UpdateFranquiciaUseCase updateFranquiciaUseCase) {
		this.createFranquiciaUseCase = createFranquiciaUseCase;
		this.updateFranquiciaUseCase = updateFranquiciaUseCase;
	}

	@Override
	public Mono<Franquicia> save(Franquicia franquicia) {
		return createFranquiciaUseCase.save(franquicia);
	}

	@Override
	public Mono<Franquicia> updateNombre(Long id, String nombre) {
		return updateFranquiciaUseCase.updateNombre(id, nombre);
	}

}
