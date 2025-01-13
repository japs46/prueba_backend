package com.backend.reactivo.app.aplication.usecases;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.reactivo.app.domain.model.ProductoSucursal;
import com.backend.reactivo.app.domain.ports.out.ProductoRepositoryPort;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class RetrieveProductoUseCaseImplTest {
	
	@Mock
	private ProductoRepositoryPort productoRepositoryPort;
	
	@InjectMocks
	private RetrieveProductoUseCaseImpl retrieveProductoUseCase;
	
	@Test
	void findProductoConMayorStockPorFranquiciaTest() {

		Long franquiciaId = 1L;

        ProductoSucursal producto1 = new ProductoSucursal(1L, "Producto A", 4L,1L,"sucursal1");
        ProductoSucursal producto2 = new ProductoSucursal(2L, "Producto B", 5L,2L,"sucursal2");

        when(productoRepositoryPort.findProductoConMayorStockPorFranquicia(anyLong()))
                .thenReturn(Flux.just(producto1, producto2));

        Flux<ProductoSucursal> result = retrieveProductoUseCase.findProductoConMayorStockPorFranquicia(franquiciaId);

        StepVerifier.create(result)
                .expectNextMatches(producto -> producto.getProductoId().equals(1L) && producto.getProductoStock() == 4L
                && producto.getProductoNombre().equals("Producto A") && producto.getSucursalNombre().equals("sucursal1"))
                .expectNextMatches(producto -> producto.getProductoId().equals(2L) && producto.getProductoStock() == 5L)
                .verifyComplete();
	}

}
