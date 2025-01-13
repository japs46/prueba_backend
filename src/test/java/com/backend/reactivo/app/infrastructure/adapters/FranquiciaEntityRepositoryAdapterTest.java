package com.backend.reactivo.app.infrastructure.adapters;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.infrastructure.entities.FranquiciaEntity;
import com.backend.reactivo.app.infrastructure.repositories.FranquiciaEntityRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class FranquiciaEntityRepositoryAdapterTest {

	@Mock
	private FranquiciaEntityRepository franquiciaEntityRepository;

	@InjectMocks
	private FranquiciaEntityRepositoryAdapter franquiciaEntityRepositoryAdapter;

	@Test
	void saveTest() {

		FranquiciaEntity franquiciaEntity = new FranquiciaEntity(1L, "test");
		Franquicia franquicia = new Franquicia(1L, "test");

		when(franquiciaEntityRepository.save(any())).thenReturn(Mono.just(franquiciaEntity));

		Mono<Franquicia> franquiciaMono = franquiciaEntityRepositoryAdapter.save(franquicia);

		StepVerifier.create(franquiciaMono).expectNextMatches(f -> f.getId().equals(1L) && f.getNombre().equals("test"))
				.verifyComplete();
	}
	
	@Test
	void findByIdTest() {

		FranquiciaEntity franquiciaEntity = new FranquiciaEntity(1L, "test");

		when(franquiciaEntityRepository.findById(anyLong())).thenReturn(Mono.just(franquiciaEntity));

		Mono<Franquicia> franquiciaMono = franquiciaEntityRepositoryAdapter.findById(1L);

		StepVerifier.create(franquiciaMono).expectNextMatches(f -> f.getId().equals(1L) && f.getNombre().equals("test"))
				.verifyComplete();
	}

}
