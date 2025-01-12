package com.backend.reactivo.app.infrastructure.handler;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.application.services.FranquiciaService;
import com.backend.reactivo.app.domain.model.Franquicia;

import reactor.core.publisher.Mono;

@Component
public class FranquiciaHandler {

	private final FranquiciaService franquiciaService;
	private final Validator validator;
	
	public FranquiciaHandler(FranquiciaService franquiciaService, Validator validator) {
		this.franquiciaService = franquiciaService;
		this.validator = validator;
	}

	public Mono<ServerResponse> create(ServerRequest serverRequest){
		
		Mono<Franquicia> monoFranquisia = serverRequest.bodyToMono(Franquicia.class);
		
		return monoFranquisia.flatMap(franquisia ->{
			BindException errors = new BindException(franquisia, Franquicia.class.getName());
            validator.validate(franquisia, errors);
            if (errors.hasErrors()) {
                return Mono.error(errors);
            }
			return franquiciaService.save(franquisia);
		}).flatMap(franquisia ->{
			return ServerResponse.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(franquisia);
		}).onErrorResume(BindException.class, ex -> {
			List<String> errores = ex.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage()) 
                    .toList();
            return ServerResponse.badRequest().bodyValue(errores);
        });
				
	}
}
