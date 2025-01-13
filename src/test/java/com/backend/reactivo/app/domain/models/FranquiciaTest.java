package com.backend.reactivo.app.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.backend.reactivo.app.domain.model.Franquicia;

public class FranquiciaTest {

	@Test
	public void franquiciaTest() {
		
		String toStringEsperado = "Franquicia [id=1, nombre=test]";
		
		Long id=1L;
		String nombre= "test";
		
		Franquicia franquicia = new Franquicia(id, nombre);
		
		String toStringActual = franquicia.toString();
		
		assertNotNull(franquicia);
		assertEquals(id,franquicia.getId());
		assertEquals(nombre,franquicia.getNombre());
		assertEquals(toStringEsperado, toStringActual);
	}
}
