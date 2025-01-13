package com.backend.reactivo.app.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Franquicia {

	private final Long id;
	
	@NotEmpty(message = "El campo nombre no puede ser null o vacio")
	@Size(max = 255, message= "El campo nombre debe tener 255 caracteres como maximo")
	private final String nombre;
	
	public Franquicia(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "Franquicia [id=" + id + ", nombre=" + nombre + "]";
	}
	
}
