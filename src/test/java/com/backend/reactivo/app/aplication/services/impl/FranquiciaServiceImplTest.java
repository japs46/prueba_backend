package com.backend.reactivo.app.aplication.services.impl;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.in.CreateFranquiciaUseCase;
import com.backend.reactivo.app.domain.ports.in.UpdateFranquiciaUseCase;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class FranquiciaServiceImplTest {

	@Mock
	private CreateFranquiciaUseCase createFranquiciaUseCase;
	
	@Mock
	private UpdateFranquiciaUseCase updateFranquiciaUseCase;
	
	@InjectMocks
	private FranquiciaServiceImpl franquiciaService;
	
	@Test
	void savetest() {
		
		Franquicia franquicia = new Franquicia(1L, "test");
		
		when(createFranquiciaUseCase.save(franquicia)).thenReturn(Mono.just(franquicia));

		Mono<Franquicia> franquiciaMono = franquiciaService.save(franquicia);

		StepVerifier
		.create(franquiciaMono)
		.expectNextMatches(f -> f.getId().equals(1L) && f.getNombre().equals("test"))
				.verifyComplete();
	}
	
	@Test
	void updateNombreSuccesstest() {
		
		Franquicia franquicia = new Franquicia(1L, "test");
		
		when(updateFranquiciaUseCase.updateNombre(anyLong(),anyString())).thenReturn(Mono.just(franquicia));

		Mono<Franquicia> franquiciaMono = franquiciaService.updateNombre(1L,"test");

		StepVerifier
		.create(franquiciaMono)
		.expectNextMatches(f -> f.getId().equals(1L) 
				&& f.getNombre().equals("test"))
				.verifyComplete();
	}
	
}
