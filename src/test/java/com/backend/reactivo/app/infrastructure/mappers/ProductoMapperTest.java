package com.backend.reactivo.app.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.infrastructure.entities.ProductoEntity;

public class ProductoMapperTest {

	@Test
	public void productoMapperToDomainTest() {
		
		ProductoMapper productoMapper = new ProductoMapper();
		
		ProductoEntity entity = new ProductoEntity(1L, "test" , 3L,1L);

		Producto domain = ProductoMapper.toDomain(entity);

		assertNotNull(domain);
		assertEquals(1L, domain.getId());
		assertEquals("test", domain.getNombre());
		assertEquals(3L, domain.getStock());
		assertEquals(1L, domain.getIdSucursal());
	}
	
	@Test
	public void productoMapperToEntityTest() {
		
		Producto domain = new Producto(1L, "test" , 3L,1L);

		ProductoEntity entity = ProductoMapper.toEntity(domain);

		assertNotNull(entity);
		assertEquals(1L, entity.getId());
		assertEquals("test", entity.getNombre());
		assertEquals(3L, entity.getStock());
		assertEquals(1L, entity.getIdSucursal());
	}
	
	@Test
	public void productoMapperToEntityIdNullTest() {
		
		Producto domain = new Producto(null, "test",1L,1L);

		ProductoEntity entity = ProductoMapper.toEntity(domain);

		assertNotNull(entity);
		assertEquals(null, entity.getId());
		assertEquals("test", entity.getNombre());
		assertEquals(1L, entity.getStock());
	}
}
