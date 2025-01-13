package com.backend.reactivo.app.aplication.usecases;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.out.FranquiciaRepositoryPort;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class RetrieveFranquiciaUseCaseImplTest {

	@Mock
	private FranquiciaRepositoryPort franquiciaRepositoryPort;
	
	@InjectMocks
	private RetrieveFranquiciaUseCaseImpl retrieveFranquiciaUseCase;
	
	@Test
	void savetest() {
		
		Franquicia franquicia = new Franquicia(1L, "test");
		
		when(franquiciaRepositoryPort.findById(anyLong())).thenReturn(Mono.just(franquicia));

		Mono<Franquicia> franquiciaMono = retrieveFranquiciaUseCase.findById(1L);

		StepVerifier
		.create(franquiciaMono)
		.expectNextMatches(f -> f.getId().equals(1L) && f.getNombre().equals("test"))
				.verifyComplete();
	}
}
