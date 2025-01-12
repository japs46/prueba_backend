package com.backend.reactivo.app.application.services.impl;

import org.springframework.stereotype.Service;

import com.backend.reactivo.app.application.services.FranquiciaService;
import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.in.CreateFranquiciaUseCase;

import reactor.core.publisher.Mono;

@Service
public class FranquiciaServiceImpl implements FranquiciaService{
	
	private final CreateFranquiciaUseCase createFranquiciaUseCase;

	public FranquiciaServiceImpl(CreateFranquiciaUseCase createFranquiciaUseCase) {
		this.createFranquiciaUseCase = createFranquiciaUseCase;
	}

	@Override
	public Mono<Franquicia> save(Franquicia franquicia) {
		return createFranquiciaUseCase.save(franquicia);
	}

}
