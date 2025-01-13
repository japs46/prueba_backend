package com.backend.reactivo.app.infrastructure.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.aplication.request.UpdateStockRequest;
import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.infrastructure.config.RouterFunctionConfig;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class ProductoHandlerTest {

	@Mock
	private ProductoHandler handler;
	
	@InjectMocks 
	private RouterFunctionConfig routerFunctionConfig;
	
	@Test
	public void createProductoTest() {
		Producto producto = new Producto(1L, "test", 3L,1L);
		
		Mono<ServerResponse> serverResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(producto);
		
		when(handler.create(any())).thenReturn(serverResponse);
		
		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().post().uri("/api/producto").contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON).body(Mono.just(producto), Producto.class).exchange()
		.expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.nombre").isEqualTo("test")
		.jsonPath("$.stock").isEqualTo(3);
	}
	
	@Test
	void createProductoErrorsTest() {
		Producto producto = new Producto(null, "", 3L,1L);
		
		List<String> errors = Arrays.asList("El campo nombre no puede ser null o vacio");
		
		Mono<ServerResponse> serverResponse = ServerResponse.badRequest().bodyValue(errors);
		
		when(handler.create(any())).thenReturn(serverResponse);
		
		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().post().uri("/api/producto").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(producto), Producto.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$[0]").isEqualTo("El campo nombre no puede ser null o vacio");

	}
	
	@Test
	void createProductoNullTest() {
		
		IllegalArgumentException illegalArgumentException = new IllegalArgumentException("El objeto producto no puede ser null");
		
		Mono<ServerResponse> serverResponse = ServerResponse.badRequest()
	            .contentType(MediaType.TEXT_PLAIN)
	            .bodyValue(illegalArgumentException.getMessage());
		
		when(handler.create(any())).thenReturn(serverResponse);
		
		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().post().uri("/api/producto").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.empty(), Sucursal.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.TEXT_PLAIN)
				.expectBody(String.class)
		        .isEqualTo("El objeto producto no puede ser null");
	}
	
	@Test
	void deleteProductoSuccessTest() {
	    Long productoId = 3L;
	    
	    Mono<ServerResponse> serverResponse = ServerResponse.noContent().build();
	    
	    when(handler.delete(any())).thenReturn(serverResponse);

	    WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().delete()
	        .uri("/api/producto/{id}", productoId)
	        .exchange()
	        .expectStatus().isNoContent();
	}

	@Test
	void deleteProductoNotFoundTest() {
	    Long productoId = 999L;
	    
	    String mensajeErrorTest= "Producto no encontrado con id: "+productoId;
	    
	    Mono<ServerResponse> serverResponse = ServerResponse.badRequest()
        .bodyValue("Error eliminando el producto: " + mensajeErrorTest);
	    
	    when(handler.delete(any())).thenReturn(serverResponse);

	    WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().delete()
	        .uri("/api/producto/{id}", productoId)
	        .exchange()
	        .expectStatus().isBadRequest()
	        .expectBody(String.class)
	        .isEqualTo("Error eliminando el producto: Producto no encontrado con id: " + productoId);
	}
	
	@Test
	void updateStockTest() {
	    Long productoId = 1L;
	    UpdateStockRequest updateStockRequest= new UpdateStockRequest(4L);
	    
	    Producto updatedProducto = new Producto(1L, "test", 4L,1L);
	    
	    Mono<ServerResponse> serverResponse = ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(updatedProducto);
	    
	    when(handler.updateStock(any())).thenReturn(serverResponse);

	    WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().put().uri("/api/producto/update-stock/{id}", productoId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .bodyValue(updateStockRequest)
	            .exchange()
	            .expectStatus().isOk()
	            .expectBody()
	            .jsonPath("$.id").isEqualTo(productoId)
	            .jsonPath("$.stock").isEqualTo(4L);
	}

	@Test
	void updateStockNotFoundTest() {
	    Long productoId = 999L;
	    UpdateStockRequest updateStockRequest= new UpdateStockRequest(4L);
	    
	    String mensajeErrorTest= "Producto no encontrado con id: "+productoId;
	    
	    Mono<ServerResponse> serverResponse = ServerResponse.badRequest()
        .bodyValue(mensajeErrorTest);
	    
	    when(handler.updateStock(any())).thenReturn(serverResponse);

	    WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().put().uri("/api/producto/update-stock/{id}", productoId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .bodyValue(updateStockRequest)
	            .exchange()
	            .expectStatus().isBadRequest()
	            .expectBody(String.class)
	            .isEqualTo("Producto no encontrado con id: " + productoId);
	}
	
	@Test
	void updateNombreTest() {
	    Long productoId = 1L;
	    String nombre= "test";
	    
	    Producto updatedProducto = new Producto(1L, "test", 4L,1L);
	    
	    Mono<ServerResponse> serverResponse = ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(updatedProducto);
	    
	    when(handler.updateNombre(any())).thenReturn(serverResponse);

	    WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().put().uri("/api/producto/update-nombre/{id}", productoId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .bodyValue(nombre)
	            .exchange()
	            .expectStatus().isOk()
	            .expectBody()
	            .jsonPath("$.id").isEqualTo(productoId)
	            .jsonPath("$.stock").isEqualTo(4L);
	}

	@Test
	void updateNombreNotFoundTest() {
	    Long productoId = 999L;
	    String nombre= "test";
	    
	    String mensajeErrorTest= "Producto no encontrado con id: "+productoId;
	    
	    Mono<ServerResponse> serverResponse = ServerResponse.badRequest()
        .bodyValue(mensajeErrorTest);
	    
	    when(handler.updateNombre(any())).thenReturn(serverResponse);

	    WebTestClient.bindToRouterFunction(routerFunctionConfig.routesProducto(handler))
		.build().put().uri("/api/producto/update-nombre/{id}", productoId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .bodyValue(nombre)
	            .exchange()
	            .expectStatus().isBadRequest()
	            .expectBody(String.class)
	            .isEqualTo("Producto no encontrado con id: " + productoId);
	}
}
