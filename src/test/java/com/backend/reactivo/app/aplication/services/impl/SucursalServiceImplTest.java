package com.backend.reactivo.app.aplication.services.impl;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.CreateSucursalUseCase;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SucursalServiceImplTest {

	@Mock
	private CreateSucursalUseCase createSucursalUseCase;
	
	@InjectMocks
	private SucursalServiceImpl sucursalService;
	
	@Test
	void savetest() {
		
		Sucursal sucursal = new Sucursal(1L, "test",1L);
		
		when(createSucursalUseCase.save(sucursal)).thenReturn(Mono.just(sucursal));

		Mono<Sucursal> sucursalMono = sucursalService.save(sucursal);

		StepVerifier
		.create(sucursalMono)
		.expectNextMatches(f -> f.getId().equals(1L) 
				&& f.getNombre().equals("test")
				&& f.getIdFranquicia().equals(1L))
				.verifyComplete();
	}
}
