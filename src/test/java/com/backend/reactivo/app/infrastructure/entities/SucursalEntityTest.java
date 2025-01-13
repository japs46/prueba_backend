package com.backend.reactivo.app.infrastructure.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class SucursalEntityTest {

	@Test
	public void sucursalEntityTest() {
		
		String toStringEsperado = "SucursalEntity [id=1, nombre=test, idFranquicia=1]";
		
		Long id=1L;
		String nombre= "test";
		Long idFranquicia = 1L;
		
		SucursalEntity sucursalEntity = new SucursalEntity(id, nombre,idFranquicia);
		
		String toStringActual = sucursalEntity.toString();
		
		assertNotNull(sucursalEntity);
		assertEquals(id,sucursalEntity.getId());
		assertEquals(nombre,sucursalEntity.getNombre());
		assertEquals(idFranquicia,sucursalEntity.getIdFranquicia());
		assertEquals(toStringEsperado, toStringActual);
	}
}
