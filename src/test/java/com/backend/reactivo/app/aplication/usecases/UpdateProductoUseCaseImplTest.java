package com.backend.reactivo.app.aplication.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UpdateProductoUseCaseImplTest {

	@Mock
	private ProductoRepositoryPort productoRepositoryPort;
	
	@InjectMocks
	private UpdateProductoUseCaseImpl updateProductoUseCase;
	
	@Test
	void updateStockSuccess() {
		
		Producto productoBd = new Producto(1L, "test",2L,1L);
		Producto productoUpdate = new Producto(1L, "test",4L,1L);
		
		when(productoRepositoryPort.findById(anyLong())).thenReturn(Mono.just(productoBd));
		when(productoRepositoryPort.save(any())).thenReturn(Mono.just(productoUpdate));

		Mono<Producto> productoMono = updateProductoUseCase.updateStock(1L, 4L);

		StepVerifier
		.create(productoMono)
		.expectNextMatches(p -> p.getId().equals(1L) 
				&& p.getNombre().equals("test")
				&& p.getStock().equals(4L)
				&& p.getIdSucursal().equals(1L))
				.verifyComplete();
	}
	
	@Test
	void updateNombreSuccess() {
		
		Producto productoBd = new Producto(1L, "test",2L,1L);
		Producto productoUpdate = new Producto(1L, "testEditado",2L,1L);
		
		when(productoRepositoryPort.findById(anyLong())).thenReturn(Mono.just(productoBd));
		when(productoRepositoryPort.save(any())).thenReturn(Mono.just(productoUpdate));

		Mono<Producto> productoMono = updateProductoUseCase.updateNombre(1L, "testEditado");

		StepVerifier
		.create(productoMono)
		.expectNextMatches(p -> p.getId().equals(1L) 
				&& p.getNombre().equals("testEditado")
				&& p.getStock().equals(2L)
				&& p.getIdSucursal().equals(1L))
				.verifyComplete();
	}
	
}
