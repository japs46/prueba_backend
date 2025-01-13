package com.backend.reactivo.app.infrastructure.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class FranquiciaEntityTest {

	@Test
	public void franquiciaEntityTest() {
		
		String toStringEsperado = "FranquiciaEntity [id=1, nombre=test]";
		
		Long id=1L;
		String nombre= "test";
		
		FranquiciaEntity franquiciaEntity = new FranquiciaEntity(id, nombre);
		
		String toStringActual = franquiciaEntity.toString();
		
		assertNotNull(franquiciaEntity);
		assertEquals(id,franquiciaEntity.getId());
		assertEquals(nombre,franquiciaEntity.getNombre());
		assertEquals(toStringEsperado, toStringActual);
	}
}
