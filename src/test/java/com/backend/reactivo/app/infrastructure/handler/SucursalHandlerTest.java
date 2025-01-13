package com.backend.reactivo.app.infrastructure.handler;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.backend.reactivo.app.domain.model.Sucursal;

import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SucursalHandlerTest {
	
	@Autowired 
	private WebTestClient webTestClient;
	
	@Test
	public void createSucursalTest() {
		Sucursal sucursal = new Sucursal(null, "test", 1L);
		
		webTestClient.post().uri("/api/sucursal").contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON).body(Mono.just(sucursal), Sucursal.class).exchange()
		.expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
		.jsonPath("$.id").isNotEmpty().jsonPath("$.nombre").isEqualTo("test");
	}
	
	@Test
	void createSucursalErrorsTest() {
		Sucursal sucursal = new Sucursal(null, "", 1L);
		
		webTestClient.post().uri("/api/sucursal").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(sucursal), Sucursal.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$[0]").isEqualTo("El campo nombre no puede ser null o vacio");

	}
	
	@Test
	void createSucursalNullTest() {
		
		webTestClient.post().uri("/api/sucursal").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.empty(), Sucursal.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.TEXT_PLAIN)
				.expectBody(String.class)
		        .isEqualTo("El objeto sucursal no puede ser null");
	}

}
