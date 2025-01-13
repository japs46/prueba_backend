package com.backend.reactivo.app.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.backend.reactivo.app.domain.model.Sucursal;

public class SucursalTest {

	@Test
	public void sucursalTest() {
		
		String toStringEsperado = "Sucursal [id=1, nombre=test, idFranquicia=1]";
		
		Long id=1L;
		String nombre= "test";
		Long idFranquicia = 1L;
		
		Sucursal sucursal = new Sucursal(id, nombre,idFranquicia);
		
		String toStringActual = sucursal.toString();
		
		assertNotNull(sucursal);
		assertEquals(id,sucursal.getId());
		assertEquals(nombre,sucursal.getNombre());
		assertEquals(idFranquicia,sucursal.getIdFranquicia());
		assertEquals(toStringEsperado, toStringActual);
	}
}
