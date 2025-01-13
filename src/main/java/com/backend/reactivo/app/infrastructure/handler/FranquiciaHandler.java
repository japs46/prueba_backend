package com.backend.reactivo.app.infrastructure.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.aplication.services.FranquiciaService;
import com.backend.reactivo.app.aplication.usecases.CreateFranquiciaUseCaseImpl;
import com.backend.reactivo.app.domain.model.Franquicia;

import reactor.core.publisher.Mono;

@Component
public class FranquiciaHandler {

	private final Logger LOG = LoggerFactory.getLogger(CreateFranquiciaUseCaseImpl.class);

	private final FranquiciaService franquiciaService;

	public FranquiciaHandler(FranquiciaService franquiciaService) {
		this.franquiciaService = franquiciaService;
	}

	public Mono<ServerResponse> create(ServerRequest serverRequest){
		LOG.info("Inicio creación de nueva franquicia");
		
		Mono<Franquicia> monoFranquicia = serverRequest.bodyToMono(Franquicia.class);
		return monoFranquicia
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El objeto Franquicia no puede ser null")))
                .flatMap(franquiciaService::save)
                .flatMap(franquicia -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(franquicia))
                .onErrorResume(IllegalArgumentException.class, ex ->{
                		LOG.error("Error al guardar la franquicia: " + ex.getMessage());
                		 return ServerResponse.badRequest()
                		            .contentType(MediaType.TEXT_PLAIN)
                		            .bodyValue(ex.getMessage());
                 })
                .onErrorResume(BindException.class, ex -> {
                    List<String> errores = ex.getAllErrors().stream()
                            .map(err -> err.getDefaultMessage())
                            .toList();
                    LOG.error("Error al guardar la franquicia: " + errores);
                    return ServerResponse.badRequest().bodyValue(errores);
                }).doFinally(signalType -> {
                    LOG.info("Finalizo el proceso de creación de franquicia. Estado: " + signalType);
                });
	}
	
	public Mono<ServerResponse> updateNombre(ServerRequest serverRequest) {
	    LOG.info("Inicio de actualización de nombre franquicia");

	    Long id = Long.parseLong(serverRequest.pathVariable("id"));
	    Mono<String> monoNombre = serverRequest.bodyToMono(String.class);

	    return monoNombre
	            .flatMap(nombre -> franquiciaService.updateNombre(id, nombre))
	            .flatMap(updatedFranquicia -> ServerResponse.ok()
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .bodyValue(updatedFranquicia))
	            .onErrorResume(IllegalArgumentException.class, ex -> {
	                LOG.error("Error al actualizar el nombre: " + ex.getMessage());
	                return ServerResponse.badRequest()
	                        .bodyValue(ex.getMessage());
	            })
	            .doFinally(signalType -> {
	                LOG.info("Finalizó el proceso de actualización de nombre franquicia. Estado: " + signalType);
	            });
	}
	
}
