package com.backend.reactivo.app.aplication.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Validator;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.RetrieveFranquiciaUseCase;
import com.backend.reactivo.app.domain.ports.out.SucursalRepositoryPort;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CreateSucursalUseCaseImplTest {

	@Mock
	private SucursalRepositoryPort sucursalRepositoryPort;
	
	@Mock
	private RetrieveFranquiciaUseCase retrieveFranquiciaUseCase;
	
	@Mock
	private Validator validator;

	@InjectMocks
	private CreateSucursalUseCaseImpl createSucursalUseCaseImpl;

	@Test
	void saveTest() {
		
		Sucursal sucursal = new Sucursal(1L, "test",1L);
		Franquicia franquicia = new Franquicia(1L, "test");
		
		when(retrieveFranquiciaUseCase.findById(any())).thenReturn(Mono.just(franquicia));
		when(sucursalRepositoryPort.save(sucursal)).thenReturn(Mono.just(sucursal));
		

		Mono<Sucursal> sucursalMono = createSucursalUseCaseImpl.save(sucursal);

		StepVerifier
		.create(sucursalMono)
		.expectNextMatches(f -> f.getId().equals(1L) 
				&& f.getNombre().equals("test")
				&& f.getIdFranquicia().equals(1L))
				.verifyComplete();
	}
	
	@Test
	void testSaveWithError() {
		Sucursal sucursal = new Sucursal(1L, "test",1L);
	    Franquicia franquicia = new Franquicia(1L, "test");
	    
	    when(retrieveFranquiciaUseCase.findById(any())).thenReturn(Mono.just(franquicia));
	    
	    when(sucursalRepositoryPort.save(sucursal))
	        .thenReturn(Mono.error(new RuntimeException("Database error")));

	    Mono<Sucursal> result = createSucursalUseCaseImpl.save(sucursal);

	    StepVerifier.create(result)
	        .expectErrorMatches(throwable -> throwable instanceof RuntimeException 
	                                && throwable.getMessage().equals("Database error"))
	        .verify();
	}
	
	
}
