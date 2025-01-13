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

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.infrastructure.config.RouterFunctionConfig;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class FranquiciaHandlerTest {
	
	@Mock
	private FranquiciaHandler handler;
	
	@InjectMocks 
	private RouterFunctionConfig routerFunctionConfig;

	@Test
	void createOkTest() {
		Franquicia franquicia = new Franquicia(1L, "testPost");
		
		Mono<ServerResponse> serverResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(franquicia);
		
		when(handler.create(any())).thenReturn(serverResponse);

		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesFranquicia(handler))
		.build().post().uri("/api/franquicia").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(franquicia), Franquicia.class).exchange()
				.expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$.id").isNotEmpty().jsonPath("$.nombre").isEqualTo("testPost");
	}

	@Test
	void createErrorsTest() {
		Franquicia franquicia = new Franquicia(null, "");
		
		List<String> errors = Arrays.asList("El campo nombre no puede ser null o vacio");
		
		Mono<ServerResponse> serverResponse = ServerResponse.badRequest().bodyValue(errors);
		
		when(handler.create(any())).thenReturn(serverResponse);
		
		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesFranquicia(handler))
		.build().post().uri("/api/franquicia").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(franquicia), Franquicia.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$[0]").isEqualTo("El campo nombre no puede ser null o vacio");

	}
	
	@Test
	void createFranquiciaNullTest() {
		
		IllegalArgumentException illegalArgumentException = new IllegalArgumentException("El objeto Franquicia no puede ser null");
		
		Mono<ServerResponse> serverResponse = ServerResponse.badRequest()
	            .contentType(MediaType.TEXT_PLAIN)
	            .bodyValue(illegalArgumentException.getMessage());
		
		when(handler.create(any())).thenReturn(serverResponse);
		
		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesFranquicia(handler))
		.build().post().uri("/api/franquicia").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.empty(), Franquicia.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.TEXT_PLAIN)
				.expectBody(String.class)
		        .isEqualTo("El objeto Franquicia no puede ser null");
	}


}
