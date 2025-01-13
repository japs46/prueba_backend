package com.backend.reactivo.app.infrastructure.adapters;

import static org.mockito.ArgumentMatchers.any;
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
import com.backend.reactivo.app.infrastructure.entities.ProductoEntity;
import com.backend.reactivo.app.infrastructure.repositories.ProductoEntityRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProductoEntityRepositoryAdapterTest {

	@Mock
	private ProductoEntityRepository productoEntityRepository;

	@InjectMocks
	private ProductoEntityRepositoryAdapter productoEntityRepositoryAdapter;
	
	@Test
	void saveTest() {

		ProductoEntity productoEntity = new ProductoEntity(1L, "test",3L,1L);
		Producto producto = new Producto(1L, "test",3L,1L);

		when(productoEntityRepository.save(any())).thenReturn(Mono.just(productoEntity));

		Mono<Producto> productoMono = productoEntityRepositoryAdapter.save(producto);

		StepVerifier.create(productoMono).expectNextMatches(p -> p.getId().equals(1L) 
				&& p.getNombre().equals("test")
				&& p.getStock().equals(3L)
				&& p.getIdSucursal().equals(1L))
				.verifyComplete();
	}
	
	@Test
	void findByIdTest() {

		ProductoEntity productoEntity = new ProductoEntity(1L, "test", 3L,1L);

		when(productoEntityRepository.findById(anyLong())).thenReturn(Mono.just(productoEntity));

		Mono<Producto> productoMono = productoEntityRepositoryAdapter.findById(1L);

		StepVerifier.create(productoMono).expectNextMatches(p -> p.getId().equals(1L) 
				&& p.getNombre().equals("test")
				&& p.getStock().equals(3L))
				.verifyComplete();
	}
	
	@Test
	void deleteByIdTest() {

		Long productoId = 1L;

		when(productoEntityRepository.deleteById(anyLong())).thenReturn(Mono.empty());

		Mono<Void> result = productoEntityRepositoryAdapter.delete(productoId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productoEntityRepository).deleteById(productoId);
	}
	
	@Test
	void findProductoConMayorStockPorFranquiciaTest() {

		Long franquiciaId = 1L;

        // Simula los datos que devolvería el repositorio
        ProductoSucursal producto1 = new ProductoSucursal(1L, "Producto A", 4L,1L,"sucursal1");
        ProductoSucursal producto2 = new ProductoSucursal(2L, "Producto B", 5L,2L,"sucursal2");

        when(productoEntityRepository.findProductoConMayorStockPorFranquicia(anyLong()))
                .thenReturn(Flux.just(producto1, producto2));

        // Llama al método del adaptador
        Flux<ProductoSucursal> result = productoEntityRepositoryAdapter.findProductoConMayorStockPorFranquicia(franquiciaId);

        // Verifica los resultados con StepVerifier
        StepVerifier.create(result)
                .expectNextMatches(producto -> producto.getProductoId().equals(1L) && producto.getProductoStock() == 4L
                && producto.getProductoNombre().equals("Producto A") && producto.getSucursalNombre().equals("sucursal1"))
                .expectNextMatches(producto -> producto.getProductoId().equals(2L) && producto.getProductoStock() == 5L)
                .verifyComplete();
	}
	
}
