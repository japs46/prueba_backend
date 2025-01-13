package com.backend.reactivo.app.domain.ports.out;

import com.backend.reactivo.app.domain.model.Franquicia;

import reactor.core.publisher.Mono;

public interface FranquiciaRepositoryPort {

	public Mono<Franquicia> save(Franquicia franquicia);
}
