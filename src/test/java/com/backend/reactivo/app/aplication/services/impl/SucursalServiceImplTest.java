package com.backend.reactivo.app.aplication.services.impl;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.CreateSucursalUseCase;
import com.backend.reactivo.app.domain.ports.in.UpdateSucursalUseCase;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SucursalServiceImplTest {

	@Mock
	private CreateSucursalUseCase createSucursalUseCase;
	
	@Mock
	private UpdateSucursalUseCase updateSucursalUseCase;
	
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
	
	@Test
	void updateNombreSuccesstest() {
		
		Sucursal sucursal = new Sucursal(1L, "test",1L);
		
		when(updateSucursalUseCase.updateNombre(anyLong(),anyString())).thenReturn(Mono.just(sucursal));

		Mono<Sucursal> sucursalMono = sucursalService.updateNombre(1L,"test");

		StepVerifier
		.create(sucursalMono)
		.expectNextMatches(s -> s.getId().equals(1L) 
				&& s.getNombre().equals("test"))
				.verifyComplete();
	}
}
