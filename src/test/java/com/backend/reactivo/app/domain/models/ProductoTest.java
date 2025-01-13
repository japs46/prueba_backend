package com.backend.reactivo.app.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.backend.reactivo.app.domain.model.Producto;

public class ProductoTest {

	@Test
	public void productoTest() {
		
		String toStringEsperado = "Producto [id=1, nombre=test, stock=3, idSucursal=1]";
		
		Long id=1L;
		String nombre= "test";
		Long stock = 3L;
		Long idSucursal = 1L;
		
		Producto producto = new Producto(id, nombre,stock,idSucursal);
		
		String toStringActual = producto.toString();
		
		assertNotNull(producto);
		assertEquals(id,producto.getId());
		assertEquals(nombre,producto.getNombre());
		assertEquals(stock,producto.getStock());
		assertEquals(idSucursal,producto.getIdSucursal());
		assertEquals(toStringEsperado, toStringActual);
	}
}
