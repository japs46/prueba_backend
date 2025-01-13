package com.backend.reactivo.app.aplication.usecases;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Validator;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.out.FranquiciaRepositoryPort;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CreateFranquiciaUseCaseImplTest {

	@Mock
	private FranquiciaRepositoryPort franquiciaRepositoryPort;
	
	@Mock
	private Validator validator;

	@InjectMocks
	private CreateFranquiciaUseCaseImpl createFranquiciaUseCase;

	@Test
	void saveTest() {
		
		Franquicia franquicia = new Franquicia(1L, "test");
		
		when(franquiciaRepositoryPort.save(franquicia)).thenReturn(Mono.just(franquicia));

		Mono<Franquicia> franquiciaMono = createFranquiciaUseCase.save(franquicia);

		StepVerifier
		.create(franquiciaMono)
		.expectNextMatches(f -> f.getId().equals(1L) && f.getNombre().equals("test"))
				.verifyComplete();
	}
	
	@Test
	void testSaveWithError() {
	    Franquicia franquicia = new Franquicia(1L, "test");
	    when(franquiciaRepositoryPort.save(franquicia))
	        .thenReturn(Mono.error(new RuntimeException("Database error")));

	    Mono<Franquicia> result = createFranquiciaUseCase.save(franquicia);

	    StepVerifier.create(result)
	        .expectErrorMatches(throwable -> throwable instanceof RuntimeException 
	                                && throwable.getMessage().equals("Database error"))
	        .verify();
	}

}
