package com.backend.reactivo.app.aplication.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Validator;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.RetrieveSucursalUseCase;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CreateProductoUseCaseImplTest {

	@Mock
	private ProductoRepositoryPort productoRepositoryPort;
	
	@Mock
	private RetrieveSucursalUseCase retrieveSucursalUseCase;
	
	@Mock
	private Validator validator;
	
	@InjectMocks
	private CreateProductoUseCaseImpl createProductoUseCase;

	@Test
	void saveTest() {
		
		Producto producto = new Producto(1L, "test",3L,1L);
		Sucursal sucursal = new Sucursal(1L, "test",1L);
		
		when(retrieveSucursalUseCase.findById(any())).thenReturn(Mono.just(sucursal));
		when(productoRepositoryPort.save(producto)).thenReturn(Mono.just(producto));
		

		Mono<Producto> productoMono = createProductoUseCase.save(producto);

		StepVerifier
		.create(productoMono)
		.expectNextMatches(f -> f.getId().equals(1L) 
				&& f.getNombre().equals("test")
				&& f.getStock().equals(3L)
				&& f.getIdSucursal().equals(1L))
				.verifyComplete();
	}
	
	@Test
	void testSaveWithError() {
		Producto producto = new Producto(1L, "test",3L,1L);
		Sucursal sucursal = new Sucursal(1L, "test",1L);
	    
	    when(retrieveSucursalUseCase.findById(any())).thenReturn(Mono.just(sucursal));
	    
	    when(productoRepositoryPort.save(producto))
	        .thenReturn(Mono.error(new RuntimeException("Database error")));

	    Mono<Producto> productoMono = createProductoUseCase.save(producto);

	    StepVerifier.create(productoMono)
	        .expectErrorMatches(throwable -> throwable instanceof RuntimeException 
	                                && throwable.getMessage().equals("Database error"))
	        .verify();
	}
}
