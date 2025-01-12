package com.backend.reactivo.app.domain.model;

public class Franquicia {

	private final Long id;
	
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
