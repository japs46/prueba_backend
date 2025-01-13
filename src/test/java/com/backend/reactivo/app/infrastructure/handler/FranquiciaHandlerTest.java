package com.backend.reactivo.app.infrastructure.handler;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.backend.reactivo.app.domain.model.Franquicia;

import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class FranquiciaHandlerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void createTest() {
		Franquicia franquicia = new Franquicia(null, "testPost");

		webTestClient.post().uri("/api/franquicia").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(franquicia), Franquicia.class).exchange()
				.expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$.id").isNotEmpty().jsonPath("$.nombre").isEqualTo("testPost");
	}

	@Test
	void createErrorsTest() {
		Franquicia franquicia = new Franquicia(null, "");
		
		webTestClient.post().uri("/api/franquicia").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(franquicia), Franquicia.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$[0]").isEqualTo("El campo nombre no puede ser null o vacio");

	}
	
	@Test
	void createFranquiciaNullTest() {
		
		webTestClient.post().uri("/api/franquicia").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.empty(), Franquicia.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.TEXT_PLAIN)
				.expectBody(String.class) // Validamos que el cuerpo sea un string
		        .isEqualTo("El objeto Franquicia no puede ser null");
	}


}
