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
import com.backend.reactivo.app.infrastructure.entities.ProductoEntity;
import com.backend.reactivo.app.infrastructure.repositories.ProductoEntityRepository;

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
	
}
