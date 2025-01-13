package com.backend.reactivo.app.infrastructure.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.aplication.services.SucursalService;
import com.backend.reactivo.app.domain.model.Sucursal;

import reactor.core.publisher.Mono;

@Component
public class SucursalHandler {
	
	private final Logger LOG = LoggerFactory.getLogger(SucursalHandler.class);

	private final SucursalService sucursalService;

	public SucursalHandler(SucursalService sucursalService) {
		this.sucursalService = sucursalService;
	}
	
	public Mono<ServerResponse> create(ServerRequest serverRequest){
		LOG.info("Inicio creación de nueva sucursal");
		
		Mono<Sucursal> monoSucursal = serverRequest.bodyToMono(Sucursal.class);
		return monoSucursal
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El objeto sucursal no puede ser null")))
                .flatMap(sucursalService::save)
                .flatMap(sucursal -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(sucursal))
                .onErrorResume(IllegalArgumentException.class, ex ->{
                		LOG.error("Error al guardar la sucursal: " + ex.getMessage());
                		 return ServerResponse.badRequest()
                		            .contentType(MediaType.TEXT_PLAIN)
                		            .bodyValue(ex.getMessage());
                 })
                .onErrorResume(BindException.class, ex -> {
                    List<String> errores = ex.getAllErrors().stream()
                            .map(err -> err.getDefaultMessage())
                            .toList();
                    LOG.error("Error al guardar la sucursal: " + errores);
                    return ServerResponse.badRequest().bodyValue(errores);
                }).doFinally(signalType -> {
                    LOG.info("Finalizo el proceso de creación de sucursal. Estado: " + signalType);
                });
	}
	
	public Mono<ServerResponse> updateNombre(ServerRequest serverRequest) {
	    LOG.info("Inicio de actualización de nombre sucursal");

	    Long id = Long.parseLong(serverRequest.pathVariable("id"));
	    Mono<String> monoNombre = serverRequest.bodyToMono(String.class);

	    return monoNombre
	            .flatMap(nombre -> sucursalService.updateNombre(id, nombre))
	            .flatMap(updatedSucursal -> ServerResponse.ok()
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .bodyValue(updatedSucursal))
	            .onErrorResume(IllegalArgumentException.class, ex -> {
	                LOG.error("Error al actualizar el nombre: " + ex.getMessage());
	                return ServerResponse.badRequest()
	                        .bodyValue(ex.getMessage());
	            })
	            .doFinally(signalType -> {
	                LOG.info("Finalizó el proceso de actualización de nombre sucursal. Estado: " + signalType);
	            });
	}

}
