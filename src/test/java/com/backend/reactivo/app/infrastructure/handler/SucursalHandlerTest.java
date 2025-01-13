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

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.infrastructure.config.RouterFunctionConfig;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class SucursalHandlerTest {
	
	@Mock
	private SucursalHandler handler;
	
	@InjectMocks 
	private RouterFunctionConfig routerFunctionConfig;
	
	@Test
	public void createSucursalOkTest() {
		Sucursal sucursal = new Sucursal(1L, "test", 1L);
		
		Mono<ServerResponse> serverResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(sucursal);
		
		when(handler.create(any())).thenReturn(serverResponse);
		
		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesSucursal(handler))
		.build().post().uri("/api/sucursal").contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON).body(Mono.just(sucursal), Sucursal.class).exchange()
		.expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
		.jsonPath("$.id").isNotEmpty().jsonPath("$.nombre").isEqualTo("test");
	}
	
	@Test
	void createSucursalErrorsTest() {
		Sucursal sucursal = new Sucursal(null, "", 1L);
		
		List<String> errors = Arrays.asList("El campo nombre no puede ser null o vacio");
		
		Mono<ServerResponse> serverResponse = ServerResponse.badRequest().bodyValue(errors);
		
		when(handler.create(any())).thenReturn(serverResponse);
		
		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesSucursal(handler))
		.build().post().uri("/api/sucursal").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(sucursal), Sucursal.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
				.jsonPath("$[0]").isEqualTo("El campo nombre no puede ser null o vacio");

	}
	
	@Test
	void createSucursalNullTest() {
		
		IllegalArgumentException illegalArgumentException = new IllegalArgumentException("El objeto sucursal no puede ser null");
		
		Mono<ServerResponse> serverResponse = ServerResponse.badRequest()
	            .contentType(MediaType.TEXT_PLAIN)
	            .bodyValue(illegalArgumentException.getMessage());
		
		when(handler.create(any())).thenReturn(serverResponse);
		
		WebTestClient.bindToRouterFunction(routerFunctionConfig.routesSucursal(handler))
		.build().post().uri("/api/sucursal").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.empty(), Sucursal.class).exchange()
				.expectStatus().isBadRequest().expectHeader().contentType(MediaType.TEXT_PLAIN)
				.expectBody(String.class)
		        .isEqualTo("El objeto sucursal no puede ser null");
	}

}
