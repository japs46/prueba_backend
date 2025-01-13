package com.backend.reactivo.app.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Sucursal {

	private final Long id;
	@NotEmpty(message = "El campo nombre no puede ser null o vacio")
	@Size(max = 255, message= "El campo nombre debe tener 255 caracteres como maximo")
	private final String nombre;
	@NotNull(message = "el campo idFranquicia no puede ser null")
	@Min(value = 1,message = "el campo idFranquicia debe ser positivo y mayor que 0")
	private final Long idFranquicia;
	
	public Sucursal(Long id, String nombre, Long idFranquicia) {
		this.id = id;
		this.nombre = nombre;
		this.idFranquicia = idFranquicia;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Long getIdFranquicia() {
		return idFranquicia;
	}

	@Override
	public String toString() {
		return "Sucursal [id=" + id + ", nombre=" + nombre + ", idFranquicia=" + idFranquicia + "]";
	}
	
}
