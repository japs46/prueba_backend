package com.backend.reactivo.app.infrastructure.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.aplication.services.ProductoService;
import com.backend.reactivo.app.domain.model.Producto;

import reactor.core.publisher.Mono;

@Component
public class ProductoHandler {

	private final Logger LOG = LoggerFactory.getLogger(ProductoHandler.class);

	private final ProductoService productoService;

	public ProductoHandler(ProductoService productoService) {
		this.productoService = productoService;
	}
	
	public Mono<ServerResponse> create(ServerRequest serverRequest){
		LOG.info("Inicio creación de nuevo producto");
		
		Mono<Producto> monoProducto = serverRequest.bodyToMono(Producto.class);
		return monoProducto
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El objeto producto no puede ser null")))
                .flatMap(productoService::save)
                .flatMap(producto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(producto))
                .onErrorResume(IllegalArgumentException.class, ex ->{
                		LOG.error("Error al guardar el producto: " + ex.getMessage());
                		 return ServerResponse.badRequest()
                		            .contentType(MediaType.TEXT_PLAIN)
                		            .bodyValue(ex.getMessage());
                 })
                .onErrorResume(BindException.class, ex -> {
                    List<String> errores = ex.getAllErrors().stream()
                            .map(err -> err.getDefaultMessage())
                            .toList();
                    LOG.error("Error al guardar el producto: " + errores);
                    return ServerResponse.badRequest().bodyValue(errores);
                }).doFinally(signalType -> {
                    LOG.info("Finalizo el proceso de creación de producto. Estado: " + signalType);
                });
	}
}
