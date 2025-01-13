package com.backend.reactivo.app.infrastructure.adapters;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.infrastructure.entities.SucursalEntity;
import com.backend.reactivo.app.infrastructure.repositories.SucursalEntityRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SucursalEntityRepositoryAdapterTest {

	@Mock
	private SucursalEntityRepository sucursalEntityRepository;

	@InjectMocks
	private SucursalEntityRepositoryAdapter sucursalEntityRepositoryAdapter;

	@Test
	void saveTest() {

		SucursalEntity sucursalEntity = new SucursalEntity(1L, "test",1L);
		Sucursal sucursal = new Sucursal(1L, "test",1L);

		when(sucursalEntityRepository.save(any())).thenReturn(Mono.just(sucursalEntity));

		Mono<Sucursal> sucursalMono = sucursalEntityRepositoryAdapter.save(sucursal);

		StepVerifier.create(sucursalMono).expectNextMatches(f -> f.getId().equals(1L) 
				&& f.getNombre().equals("test")
				&& f.getIdFranquicia().equals(1L))
				.verifyComplete();
	}
	
	@Test
	void findByIdTest() {

		SucursalEntity sucursalEntity = new SucursalEntity(1L, "test", 1L);

		when(sucursalEntityRepository.findById(anyLong())).thenReturn(Mono.just(sucursalEntity));

		Mono<Sucursal> sucursalMono = sucursalEntityRepositoryAdapter.findById(1L);

		StepVerifier.create(sucursalMono).expectNextMatches(s -> s.getId().equals(1L) 
				&& s.getNombre().equals("test")
				&& s.getIdFranquicia().equals(1L))
				.verifyComplete();
	}
}
