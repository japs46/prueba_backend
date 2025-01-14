package com.backend.reactivo.app.infrastructure.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.validation.BindException;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.aplication.services.SucursalService;
import com.backend.reactivo.app.domain.model.Sucursal;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SucursalHandlerTest {

	@Mock
	private SucursalService sucursalService;

	@InjectMocks
	private SucursalHandler sucursalHandler;

	@Test
	public void createSuccessTest() {

		Sucursal sucursal = new Sucursal(1L, "test", 1L);
		Mono<Sucursal> sucursalMono = Mono.just(sucursal);

		when(sucursalService.save(any(Sucursal.class))).thenReturn(sucursalMono);

		MockServerRequest mockRequest = MockServerRequest.builder().body(Mono.just(sucursal));

		Mono<ServerResponse> responseMono = sucursalHandler.create(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.OK, response.statusCode());
			assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
		}).verifyComplete();

		verify(sucursalService).save(any(Sucursal.class));
	}

	@Test
	void testCreateWithEmptyBody() {
		MockServerRequest mockRequest = MockServerRequest.builder().body(Mono.empty());

		Mono<ServerResponse> responseMono = sucursalHandler.create(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
			assertEquals(MediaType.TEXT_PLAIN, response.headers().getContentType());
		}).verifyComplete();

		verifyNoInteractions(sucursalService);
	}

	@Test
	void testCreateWithValidationError() {
		Sucursal sucursalError = new Sucursal(null, "",1L);
		BindException bindException = new BindException(sucursalError, "sucursal");
		bindException.rejectValue("nombre", "notBlank", "El nombre es obligatorio");

		when(sucursalService.save(any(Sucursal.class))).thenReturn(Mono.error(bindException));

		MockServerRequest mockRequest = MockServerRequest.builder().body(Mono.just(sucursalError));

		Mono<ServerResponse> responseMono = sucursalHandler.create(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
		}).verifyComplete();
	}

	@Test
	void testUpdateNombre() {
		Long id = 1L;
		String nuevoNombre = "testEditado";
		Long idFranquicia=1L;

		Sucursal sucursalActualizada = new Sucursal(id, nuevoNombre,idFranquicia);

		when(sucursalService.updateNombre(id, nuevoNombre)).thenReturn(Mono.just(sucursalActualizada));

		MockServerRequest mockRequest = MockServerRequest.builder().pathVariable("id", id.toString())
				.body(Mono.just(nuevoNombre));

		Mono<ServerResponse> responseMono = sucursalHandler.updateNombre(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.OK, response.statusCode());
			assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
		}).verifyComplete();

		verify(sucursalService).updateNombre(anyLong(), anyString());
	}

	@Test
	void testUpdateNombreWithError() {
		Long id = 1L;
		String nuevoNombre = "Nuevo Nombre Sucursal";
		String mensajeError = "Sucursal no encontrada";

		when(sucursalService.updateNombre(id, nuevoNombre))
				.thenReturn(Mono.error(new IllegalArgumentException(mensajeError)));

		MockServerRequest mockRequest = MockServerRequest.builder().pathVariable("id", id.toString())
				.body(Mono.just(nuevoNombre));

		Mono<ServerResponse> responseMono = sucursalHandler.updateNombre(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
		}).verifyComplete();
	}
}
