package com.backend.reactivo.app.aplication.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.out.SucursalRepositoryPort;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UpdateSucursalUseCaseImpltest {

	@Mock
	private SucursalRepositoryPort sucursalRepositoryPort;
	
	@InjectMocks
	private UpdateSucursalUseCaseImpl updateSucursalUseCase;
	
	@Test
	void updateNombreSuccess() {
		
		Sucursal sucursalBd = new Sucursal(1L, "test",1L);
		Sucursal sucursalUpdate = new Sucursal(1L, "testEditado",1L);
		
		when(sucursalRepositoryPort.findById(anyLong())).thenReturn(Mono.just(sucursalBd));
		when(sucursalRepositoryPort.save(any())).thenReturn(Mono.just(sucursalUpdate));

		Mono<Sucursal> sucursalMono = updateSucursalUseCase.updateNombre(1L, "testEditado");

		StepVerifier
		.create(sucursalMono)
		.expectNextMatches(s -> s.getId().equals(1L) 
				&& s.getNombre().equals("testEditado")
				&& s.getIdFranquicia().equals(1L))
				.verifyComplete();
	}
}
