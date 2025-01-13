package com.backend.reactivo.app.domain.ports.in;

import com.backend.reactivo.app.domain.model.Franquicia;

import reactor.core.publisher.Mono;

public interface RetrieveFranquiciaUseCase {

	public Mono<Franquicia> findById(Long id);
}
