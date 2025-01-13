package com.backend.reactivo.app.aplication.usecases;

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
public class RetrieveSucursalUseCaseImplTest {

	@Mock
	private SucursalRepositoryPort sucursalRepositoryPort;
	
	@InjectMocks
	private RetrieveSucursalUseCaseImpl retrieveSucursalUseCase;
	
	@Test
	void savetest() {
		
		Sucursal sucursal = new Sucursal(1L, "test", 1L);
		
		when(sucursalRepositoryPort.findById(anyLong())).thenReturn(Mono.just(sucursal));

		Mono<Sucursal> sucursalMono = retrieveSucursalUseCase.findById(1L);

		StepVerifier
		.create(sucursalMono)
		.expectNextMatches(f -> f.getId().equals(1L) 
				&& f.getNombre().equals("test")
				&& f.getIdFranquicia().equals(1L))
				.verifyComplete();
	}
}
