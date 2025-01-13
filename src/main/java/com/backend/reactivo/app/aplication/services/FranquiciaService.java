package com.backend.reactivo.app.aplication.services;

import com.backend.reactivo.app.domain.model.Franquicia;

import reactor.core.publisher.Mono;

public interface FranquiciaService {

	public Mono<Franquicia> save(Franquicia franquicia);
}
