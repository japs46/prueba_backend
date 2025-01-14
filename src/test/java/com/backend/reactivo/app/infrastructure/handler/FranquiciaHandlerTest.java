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

import com.backend.reactivo.app.aplication.services.FranquiciaService;
import com.backend.reactivo.app.domain.model.Franquicia;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class FranquiciaHandlerTest {

	@Mock
	private FranquiciaService franquiciaService;
	
	@InjectMocks
	private FranquiciaHandler franquiciaHandler;
	
	@Test
	public void createSuccessTest() {
		
        Franquicia franquicia = new Franquicia(1L, "test");
        Mono<Franquicia> franquiciaMono = Mono.just(franquicia);

        when(franquiciaService.save(any(Franquicia.class))).thenReturn(franquiciaMono);

        MockServerRequest mockRequest = MockServerRequest.builder()
                .body(Mono.just(franquicia));

        Mono<ServerResponse> responseMono = franquiciaHandler.create(mockRequest);

        StepVerifier.create(responseMono)
                .consumeNextWith(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                })
                .verifyComplete();

        verify(franquiciaService).save(any(Franquicia.class));
	}
	
	 @Test
	    void testCreateWithEmptyBody() {
	        MockServerRequest mockRequest = MockServerRequest.builder()
	                .body(Mono.empty());

	        Mono<ServerResponse> responseMono = franquiciaHandler.create(mockRequest);

	        StepVerifier.create(responseMono)
	                .consumeNextWith(response -> {
	                    assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
	                    assertEquals(MediaType.TEXT_PLAIN, response.headers().getContentType());
	                })
	                .verifyComplete();

	        verifyNoInteractions(franquiciaService);
	    }

	    @Test
	    void testCreateWithValidationError() {
	    	Franquicia franquiciaError = new Franquicia(null, "");
	        BindException bindException = new BindException(franquiciaError, "franquicia");
	        bindException.rejectValue("nombre", "notBlank", "El nombre es obligatorio");

	        when(franquiciaService.save(any(Franquicia.class))).thenReturn(Mono.error(bindException));

	        MockServerRequest mockRequest = MockServerRequest.builder()
	                .body(Mono.just(franquiciaError));

	        Mono<ServerResponse> responseMono = franquiciaHandler.create(mockRequest);

	        StepVerifier.create(responseMono)
	                .consumeNextWith(response -> {
	                    assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
	                })
	                .verifyComplete();
	    }
	    
	    @Test
	    void testUpdateNombre() {
	        Long id = 1L;
	        String nuevoNombre = "testEditado";

	        Franquicia franquiciaActualizada = new Franquicia(id, nuevoNombre);

	        when(franquiciaService.updateNombre(id, nuevoNombre)).thenReturn(Mono.just(franquiciaActualizada));

	        MockServerRequest mockRequest = MockServerRequest.builder()
	                .pathVariable("id", id.toString())
	                .body(Mono.just(nuevoNombre));

	        Mono<ServerResponse> responseMono = franquiciaHandler.updateNombre(mockRequest);

	        StepVerifier.create(responseMono)
            .consumeNextWith(response -> {
                assertEquals(HttpStatus.OK, response.statusCode());
                assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
            })
            .verifyComplete();
	        
	        verify(franquiciaService).updateNombre(anyLong(),anyString());
	    }
	    
	    @Test
	    void testUpdateNombreWithError() {
	        Long id = 1L;
	        String nuevoNombre = "Nuevo Nombre Franquicia";
	        String mensajeError = "Franquicia no encontrada";

	        when(franquiciaService.updateNombre(id, nuevoNombre))
	                .thenReturn(Mono.error(new IllegalArgumentException(mensajeError)));

	        MockServerRequest mockRequest = MockServerRequest.builder()
	                .pathVariable("id", id.toString())
	                .body(Mono.just(nuevoNombre));

	        Mono<ServerResponse> responseMono = franquiciaHandler.updateNombre(mockRequest);

	        StepVerifier.create(responseMono)
            .consumeNextWith(response -> {
                assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
            })
            .verifyComplete();
	    }

}
