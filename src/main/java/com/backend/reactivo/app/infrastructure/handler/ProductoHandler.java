package com.backend.reactivo.app.infrastructure.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.aplication.request.UpdateStockRequest;
import com.backend.reactivo.app.aplication.services.ProductoService;
import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.model.ProductoSucursal;

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
	
	public Mono<ServerResponse> delete(ServerRequest serverRequest){
		LOG.info("Inicio Eliminación de producto");
	
		Long id = Long.parseLong(serverRequest.pathVariable("id"));
		
		return productoService.delete(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> {
                    return ServerResponse.badRequest()
                            .bodyValue("Error eliminando el producto: " + e.getMessage());
                })
                .doFinally(signalType -> {
                    LOG.info("Finalizo el proceso de eliminación del producto. Estado: " + signalType);
                });
	}
	
	public Mono<ServerResponse> updateStock(ServerRequest serverRequest) {
	    LOG.info("Inicio de actualización de stock producto");

	    Long id = Long.parseLong(serverRequest.pathVariable("id"));
	    Mono<UpdateStockRequest> monoStock = serverRequest.bodyToMono(UpdateStockRequest.class);

	    return monoStock
	    		.map(UpdateStockRequest::getStock)
	            .flatMap(stock -> productoService.updateStock(id, stock))
	            .flatMap(updatedProducto -> ServerResponse.ok()
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .bodyValue(updatedProducto))
	            .onErrorResume(IllegalArgumentException.class, ex -> {
	                LOG.error("Error al actualizar el stock: " + ex.getMessage());
	                return ServerResponse.badRequest()
	                        .bodyValue(ex.getMessage());
	            })
	            .doFinally(signalType -> {
	                LOG.info("Finalizó el proceso de actualización de stock producto. Estado: " + signalType);
	            });
	}
	
	public Mono<ServerResponse> getProductosConMayorStockPorFranquicia(ServerRequest serverRequest) {
        Long franquiciaId = Long.parseLong(serverRequest.pathVariable("franquiciaId"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoService.findProductoConMayorStockPorFranquicia(franquiciaId), ProductoSucursal.class);
    }
}
