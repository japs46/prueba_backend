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

import com.backend.reactivo.app.aplication.request.UpdateStockRequest;
import com.backend.reactivo.app.aplication.services.ProductoService;
import com.backend.reactivo.app.domain.model.Producto;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProductoHandlerTest {
	
	@Mock
	private ProductoService productoService;

	@InjectMocks
	private ProductoHandler productoHandler;
	
	@Test
	public void createSuccessTest() {

		Producto producto = new Producto(1L, "test", 3L,1L);
		Mono<Producto> productoMono = Mono.just(producto);

		when(productoService.save(any())).thenReturn(productoMono);

		MockServerRequest mockRequest = MockServerRequest.builder().body(Mono.just(producto));

		Mono<ServerResponse> responseMono = productoHandler.create(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.OK, response.statusCode());
			assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
		}).verifyComplete();

		verify(productoService).save(any());
	}

	@Test
	void testCreateWithEmptyBody() {
		MockServerRequest mockRequest = MockServerRequest.builder().body(Mono.empty());

		Mono<ServerResponse> responseMono = productoHandler.create(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
			assertEquals(MediaType.TEXT_PLAIN, response.headers().getContentType());
		}).verifyComplete();

		verifyNoInteractions(productoService);
	}

	@Test
	void testCreateWithValidationError() {
		Producto productoError = new Producto(null, "",3L,1L);
		BindException bindException = new BindException(productoError, "sucursal");
		bindException.rejectValue("nombre", "notBlank", "El nombre es obligatorio");

		when(productoService.save(any())).thenReturn(Mono.error(bindException));

		MockServerRequest mockRequest = MockServerRequest.builder().body(Mono.just(productoError));

		Mono<ServerResponse> responseMono = productoHandler.create(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
		}).verifyComplete();
	}

	@Test
	void testUpdateNombreSuccess() {
		Long id = 1L;
		String nuevoNombre = "testEditado";
		Long stock = 3L;
		Long idFranquicia=1L;

		Producto productoUpdate = new Producto(id, nuevoNombre,stock,idFranquicia);

		when(productoService.updateNombre(id, nuevoNombre)).thenReturn(Mono.just(productoUpdate));

		MockServerRequest mockRequest = MockServerRequest.builder().pathVariable("id", id.toString())
				.body(Mono.just(nuevoNombre));

		Mono<ServerResponse> responseMono = productoHandler.updateNombre(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.OK, response.statusCode());
			assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
		}).verifyComplete();

		verify(productoService).updateNombre(anyLong(), anyString());
	}

	@Test
	void testUpdateNombreWithError() {
		Long id = 1L;
		String nuevoNombre = "Nuevo Nombre Producto";
		String mensajeError = "Producto no encontrada";

		when(productoService.updateNombre(id, nuevoNombre))
				.thenReturn(Mono.error(new IllegalArgumentException(mensajeError)));

		MockServerRequest mockRequest = MockServerRequest.builder().pathVariable("id", id.toString())
				.body(Mono.just(nuevoNombre));

		Mono<ServerResponse> responseMono = productoHandler.updateNombre(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
		}).verifyComplete();
	}
	
	@Test
	void testUpdateStockSuccess() {
		Long id = 1L;
		String nombre = "test";
		Long nuevoStock = 5L;
		Long idFranquicia=1L;
		
		UpdateStockRequest updateStockRequest = new UpdateStockRequest(nuevoStock);

		Producto productoUpdate = new Producto(id, nombre,nuevoStock,idFranquicia);

		when(productoService.updateStock(id, nuevoStock)).thenReturn(Mono.just(productoUpdate));

		MockServerRequest mockRequest = MockServerRequest.builder().pathVariable("id", id.toString())
				.body(Mono.just(updateStockRequest));

		Mono<ServerResponse> responseMono = productoHandler.updateStock(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.OK, response.statusCode());
			assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
		}).verifyComplete();

		verify(productoService).updateStock(anyLong(), anyLong());
	}

	@Test
	void testUpdateStockWithError() {
		Long id = 1L;
		Long nuevoStock = 5L;
		String mensajeError = "producto no encontrado";
		
		UpdateStockRequest updateStockRequest = new UpdateStockRequest(nuevoStock);

		when(productoService.updateStock(id, nuevoStock))
				.thenReturn(Mono.error(new IllegalArgumentException(mensajeError)));

		MockServerRequest mockRequest = MockServerRequest.builder().pathVariable("id", id.toString())
				.body(Mono.just(updateStockRequest));

		Mono<ServerResponse> responseMono = productoHandler.updateStock(mockRequest);

		StepVerifier.create(responseMono).consumeNextWith(response -> {
			assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
		}).verifyComplete();
	}

}
