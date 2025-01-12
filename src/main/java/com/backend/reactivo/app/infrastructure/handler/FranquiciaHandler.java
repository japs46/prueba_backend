package com.backend.reactivo.app.infrastructure.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.application.services.FranquiciaService;
import com.backend.reactivo.app.domain.model.Franquicia;

import reactor.core.publisher.Mono;

@Component
public class FranquiciaHandler {

	private final FranquiciaService franquiciaService;
	
	public FranquiciaHandler(FranquiciaService franquiciaService) {
		this.franquiciaService = franquiciaService;
	}

	public Mono<ServerResponse> create(ServerRequest serverRequest){
		
		Mono<Franquicia> monoFranquisia = serverRequest.bodyToMono(Franquicia.class);
		
		return monoFranquisia.flatMap(franquisia ->{
			return franquiciaService.save(franquisia);
		}).flatMap(franquisia -> ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(franquisia));
	}
}
