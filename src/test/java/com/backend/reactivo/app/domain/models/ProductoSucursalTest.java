package com.backend.reactivo.app.domain.models;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.backend.reactivo.app.domain.model.ProductoSucursal;

public class ProductoSucursalTest {

	@Test
	public void productoSucursalTest() {

		String toStringEsperado = "ProductoSucursal [productoId=1, productoNombre=test, productoStock=3, sucursalId=1, sucursalNombre=test]";

		Long productoId = 1L;
		String productoNombre = "test";
		Long productoStock = 3L;
		Long sucursalId = 1L;
		String sucursalNombre = "test";

		ProductoSucursal productoSucursal = new ProductoSucursal(productoId, productoNombre, productoStock, sucursalId,
				sucursalNombre);

		String toStringActual = productoSucursal.toString();

		assertNotNull(productoSucursal);
		assertEquals(productoId, productoSucursal.getProductoId());
		assertEquals(productoNombre, productoSucursal.getProductoNombre());
		assertEquals(productoStock, productoSucursal.getProductoStock());
		assertEquals(sucursalId, productoSucursal.getSucursalId());
		assertEquals(sucursalNombre, productoSucursal.getSucursalNombre());
		assertEquals(toStringEsperado, toStringActual);
	}
}
