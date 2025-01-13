package com.backend.reactivo.app.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.infrastructure.entities.SucursalEntity;

public class SucursalMapperTest {

	@Test
	public void sucursalMapperToDomainTest() {
		
		SucursalMapper sucursalMapper = new SucursalMapper();
		
		SucursalEntity entity = new SucursalEntity(1L, "test" , 1L);

		Sucursal domain = SucursalMapper.toDomain(entity);

		assertNotNull(domain);
		assertEquals(1L, domain.getId());
		assertEquals("test", domain.getNombre());
		assertEquals(1L, domain.getIdFranquicia());
	}
	
	@Test
	public void sucursalMapperToEntityTest() {
		
		Sucursal domain = new Sucursal(1L, "test" , 1L);

		SucursalEntity entity = SucursalMapper.toEntity(domain);

		assertNotNull(entity);
		assertEquals(1L, entity.getId());
		assertEquals("test", entity.getNombre());
		assertEquals(1L, entity.getIdFranquicia());
	}
	
	@Test
	public void sucursalMapperToEntityIdNullTest() {
		
		Sucursal domain = new Sucursal(null, "test",1L);

		SucursalEntity entity = SucursalMapper.toEntity(domain);

		assertNotNull(entity);
		assertEquals(null, entity.getId());
		assertEquals("test", entity.getNombre());
		assertEquals(1L, entity.getIdFranquicia());
	}
}
