package com.backend.reactivo.app.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.infrastructure.entities.FranquiciaEntity;

public class FranquiciaMapperTest {

	@Test
	public void franquiciaMapperToDomainTest() {
		
		FranquiciaMapper franquiciaMapper = new FranquiciaMapper();
		
		FranquiciaEntity entity = new FranquiciaEntity(1L, "test");

		Franquicia domain = FranquiciaMapper.toDomain(entity);

		assertNotNull(domain);
		assertEquals(1L, domain.getId());
		assertEquals("test", domain.getNombre());
	}
	
	@Test
	public void franquiciaMapperToEntityTest() {
		
		Franquicia domain = new Franquicia(1L, "test");

		FranquiciaEntity entity = FranquiciaMapper.toEntity(domain);

		assertNotNull(entity);
		assertEquals(1L, entity.getId());
		assertEquals("test", entity.getNombre());
	}
	
	@Test
	public void franquiciaMapperToEntityIdNullTest() {
		
		Franquicia domain = new Franquicia(null, "test");

		FranquiciaEntity entity = FranquiciaMapper.toEntity(domain);

		assertNotNull(entity);
		assertEquals(null, entity.getId());
		assertEquals("test", entity.getNombre());
	}
}
