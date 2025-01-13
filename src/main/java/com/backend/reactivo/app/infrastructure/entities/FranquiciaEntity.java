package com.backend.reactivo.app.infrastructure.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Franquicias")
public class FranquiciaEntity {

	@Id
	private final Long id;
	private final String nombre;
	
	public FranquiciaEntity(Long id, String nombre) {
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
		return "FranquiciaEntity [id=" + id + ", nombre=" + nombre + "]";
	}
	
}
