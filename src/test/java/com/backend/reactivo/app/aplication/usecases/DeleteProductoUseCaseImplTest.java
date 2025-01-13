package com.backend.reactivo.app.aplication.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class DeleteProductoUseCaseImplTest {

	@Mock
	private ProductoRepositoryPort productoRepositoryPort;

	@InjectMocks
	private DeleteProductoUseCaseImpl deleteProductoUseCase;

	@Test
    void deleteProductoByIdSuccessTest() {
       
        Long productoId = 1L;
        Producto producto = new Producto(productoId, "Producto Test", 3L, 1L);

        when(productoRepositoryPort.findById(productoId)).thenReturn(Mono.just(producto));
        when(productoRepositoryPort.delete(productoId)).thenReturn(Mono.empty());

        Mono<Void> result = deleteProductoUseCase.delete(productoId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productoRepositoryPort).findById(productoId);
        verify(productoRepositoryPort).delete(productoId);
    }

    @Test
    void deleteProductoByIdNotFoundTest() {
        Long productoId = 2L;
        when(productoRepositoryPort.findById(productoId)).thenReturn(Mono.empty());

        Mono<Void> result = deleteProductoUseCase.delete(productoId);

        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> {
                    assertTrue(throwable instanceof IllegalArgumentException);
                    assertEquals("Producto no encontrado con id: " + productoId, throwable.getMessage());
                })
                .verify();

        verify(productoRepositoryPort).findById(productoId);
        verify(productoRepositoryPort, never()).delete(any());
    }
}
