package com.backend.reactivo.app.infrastructure.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Productos")
public class ProductoEntity {

	@Id
	private final Long id;
	private final String nombre;
	private final Long stock;
	@Column("id_sucursal")
	private final Long idSucursal;
	
	public ProductoEntity(Long id, String nombre, Long stock, Long idSucursal) {
		this.id = id;
		this.nombre = nombre;
		this.stock = stock;
		this.idSucursal = idSucursal;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Long getStock() {
		return stock;
	}

	public Long getIdSucursal() {
		return idSucursal;
	}

	@Override
	public String toString() {
		return "ProductoEntity [id=" + id + ", nombre=" + nombre + ", stock=" + stock + ", idSucursal=" + idSucursal
				+ "]";
	}
	
}
