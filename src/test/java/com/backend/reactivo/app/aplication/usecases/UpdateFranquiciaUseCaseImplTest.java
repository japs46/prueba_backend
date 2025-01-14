package com.backend.reactivo.app.aplication.usecases;

import static org.mockito.ArgumentMatchers.any;
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
public class UpdateFranquiciaUseCaseImplTest {

	@Mock
	private FranquiciaRepositoryPort franquiciaRepositoryPort;
	
	@InjectMocks
	private UpdateFranquiciaUseCaseImpl updateFranquiciaUseCase;
	
	@Test
	void updateNombreSuccess() {
		
		Franquicia franquiciaBd = new Franquicia(1L, "test");
		Franquicia franquiciaUpdate = new Franquicia(1L, "testEditado");
		
		when(franquiciaRepositoryPort.findById(anyLong())).thenReturn(Mono.just(franquiciaBd));
		when(franquiciaRepositoryPort.save(any())).thenReturn(Mono.just(franquiciaUpdate));

		Mono<Franquicia> franquiciaMono = updateFranquiciaUseCase.updateNombre(1L, "testEditado");

		StepVerifier
		.create(franquiciaMono)
		.expectNextMatches(f -> f.getId().equals(1L) 
				&& f.getNombre().equals("testEditado"))
				.verifyComplete();
	}
}
