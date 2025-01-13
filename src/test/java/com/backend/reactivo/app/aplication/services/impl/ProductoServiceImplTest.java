package com.backend.reactivo.app.aplication.services.impl;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.model.ProductoSucursal;
import com.backend.reactivo.app.domain.ports.in.CreateProductoUseCase;
import com.backend.reactivo.app.domain.ports.in.DeleteProductoUseCase;
import com.backend.reactivo.app.domain.ports.in.RetrieveProductoUseCase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

	@Mock
	private CreateProductoUseCase createProductoUseCase;
	
	@Mock
	private DeleteProductoUseCase deleteProductoUseCase;
	
	@Mock
	private RetrieveProductoUseCase retrieveProductoUseCase;
	
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
	
	@Test
	void findProductoConMayorStockPorFranquiciaTest() {

		Long franquiciaId = 1L;

        // Simula los datos que devolvería el repositorio
        ProductoSucursal producto1 = new ProductoSucursal(1L, "Producto A", 4L,1L,"sucursal1");
        ProductoSucursal producto2 = new ProductoSucursal(2L, "Producto B", 5L,2L,"sucursal2");

        when(retrieveProductoUseCase.findProductoConMayorStockPorFranquicia(anyLong()))
                .thenReturn(Flux.just(producto1, producto2));

        // Llama al método del adaptador
        Flux<ProductoSucursal> result = productoService.findProductoConMayorStockPorFranquicia(franquiciaId);

        // Verifica los resultados con StepVerifier
        StepVerifier.create(result)
                .expectNextMatches(producto -> producto.getProductoId().equals(1L) && producto.getProductoStock() == 4L
                && producto.getProductoNombre().equals("Producto A") && producto.getSucursalNombre().equals("sucursal1"))
                .expectNextMatches(producto -> producto.getProductoId().equals(2L) && producto.getProductoStock() == 5L)
                .verifyComplete();
	}
	
}
