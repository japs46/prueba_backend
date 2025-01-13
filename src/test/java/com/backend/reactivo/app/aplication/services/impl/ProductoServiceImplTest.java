package com.backend.reactivo.app.aplication.services.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.in.CreateProductoUseCase;
import com.backend.reactivo.app.domain.ports.in.DeleteProductoUseCase;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

	@Mock
	private CreateProductoUseCase createProductoUseCase;
	
	@Mock
	private DeleteProductoUseCase deleteProductoUseCase;
	
	@InjectMocks
	private ProductoServiceImpl productoService;
	
	@Test
	void savetest() {
		
		Producto producto = new Producto(1L, "test",3L,1L);
		
		when(createProductoUseCase.save(producto)).thenReturn(Mono.just(producto));

		Mono<Producto> productoMono = productoService.save(producto);

		StepVerifier
		.create(productoMono)
		.expectNextMatches(p -> p.getId().equals(1L) 
				&& p.getNombre().equals("test")
				&& p.getStock().equals(3L)
				&& p.getIdSucursal().equals(1L))
				.verifyComplete();
	}
	
	@Test
	void deleteTest() {

        Long productoId = 1L;

        when(deleteProductoUseCase.delete(productoId)).thenReturn(Mono.empty());

        Mono<Void> result = productoService.delete(productoId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(deleteProductoUseCase).delete(productoId);
	}
}
