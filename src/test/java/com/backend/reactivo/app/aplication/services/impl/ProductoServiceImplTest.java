package com.backend.reactivo.app.aplication.services.impl;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.in.CreateProductoUseCase;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

	@Mock
	private CreateProductoUseCase createProductoUseCase;
	
	@InjectMocks
	private ProductoServiceImpl sucursalService;
	
	@Test
	void savetest() {
		
		Producto producto = new Producto(1L, "test",3L,1L);
		
		when(createProductoUseCase.save(producto)).thenReturn(Mono.just(producto));

		Mono<Producto> productoMono = sucursalService.save(producto);

		StepVerifier
		.create(productoMono)
		.expectNextMatches(f -> f.getId().equals(1L) 
				&& f.getNombre().equals("test")
				&& f.getStock().equals(3L)
				&& f.getIdSucursal().equals(1L))
				.verifyComplete();
	}
}
