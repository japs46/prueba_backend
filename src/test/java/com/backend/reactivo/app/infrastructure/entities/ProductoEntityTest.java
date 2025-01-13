package com.backend.reactivo.app.infrastructure.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ProductoEntityTest {

	@Test
	public void productoEntityTest() {
		
		String toStringEsperado = "ProductoEntity [id=1, nombre=test, stock=3, idSucursal=1]";
		
		Long id=1L;
		String nombre= "test";
		Long stock = 3L;
		Long idSucursal = 1L;
		
		ProductoEntity productoEntity = new ProductoEntity(id, nombre,stock,idSucursal);
		
		String toStringActual = productoEntity.toString();
		
		assertNotNull(productoEntity);
		assertEquals(id,productoEntity.getId());
		assertEquals(nombre,productoEntity.getNombre());
		assertEquals(stock,productoEntity.getStock());
		assertEquals(idSucursal,productoEntity.getIdSucursal());
		assertEquals(toStringEsperado, toStringActual);
	}
}
