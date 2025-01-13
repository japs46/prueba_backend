package com.backend.reactivo.app.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Producto {

	private final Long id;
	@NotEmpty(message = "El campo nombre no puede ser null o vacio")
	@Size(max = 255, message= "El campo nombre debe tener 255 caracteres como maximo")
	private final String nombre;
	@NotNull(message = "el campo stock no puede ser null")
	@Min(value = 1,message = "el campo stock debe ser positivo y mayor que 0")
	private final Long stock;
	@NotNull(message = "el campo idSucursal no puede ser null")
	@Min(value = 1,message = "el campo idSucursal debe ser positivo y mayor que 0")
	private final Long idSucursal;
	
	public Producto(Long id, String nombre, Long stock ,Long idSucursal) {
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
		return "Producto [id=" + id + ", nombre=" + nombre + ", stock=" + stock + ", idSucursal=" + idSucursal + "]";
	}
	
}
