package com.backend.reactivo.app.infrastructure.handler;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.backend.reactivo.app.aplication.request.UpdateStockRequest;
import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.model.Sucursal;

import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductoHandlerTest {

	@Autowired 
	private WebTestClient webTestClient;
	
	@Test
	public void createProductoTest() {
		Producto producto = new Producto(null, "test", 3L,1L);
		
		webTestClient.post().uri("/api/producto").contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON).body(Mono.just(producto), Producto.class).exchange()
		.expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.nombre").isEqualTo("test")
		.jsonPath("$.stock").isEqualTo(3);
	}
	
	@Test
	void createProductoErrorsTest() {
		Producto producto = new Producto(null, "", 3L,1L);
		
		webTestClient.post().uri("/api/producto").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(producto), Producto.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$[0]").isEqualTo("El campo nombre no puede ser null o vacio");

	}
	
	@Test
	void createProductoNullTest() {
		
		webTestClient.post().uri("/api/producto").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.empty(), Sucursal.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.TEXT_PLAIN)
				.expectBody(String.class)
		        .isEqualTo("El objeto producto no puede ser null");
	}
	
	/*@Test
	void deleteProductoSuccessTest() {
	    Long productoId = 3L;

	    webTestClient.delete()
	        .uri("/api/producto/{id}", productoId)
	        .exchange()
	        .expectStatus().isNoContent();
	}*/

	@Test
	void deleteProductoNotFoundTest() {
	    Long productoId = 999L;

	    webTestClient.delete()
	        .uri("/api/producto/{id}", productoId)
	        .exchange()
	        .expectStatus().isBadRequest()
	        .expectBody(String.class)
	        .isEqualTo("Error eliminando el producto: Producto no encontrado con id: " + productoId);
	}
	
	@Test
	void updateStockTest() {
	    Long productoId = 4L;
	    UpdateStockRequest updateStockRequest= new UpdateStockRequest(4L);

	    webTestClient.put().uri("/api/producto/update-stock/{id}", productoId)
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

	    webTestClient.put().uri("/api/producto/update-stock/{id}", productoId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .bodyValue(updateStockRequest)
	            .exchange()
	            .expectStatus().isBadRequest()
	            .expectBody(String.class)
	            .isEqualTo("Producto no encontrado con id: " + productoId);
	}
}
