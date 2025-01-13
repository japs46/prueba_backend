package com.backend.reactivo.app.infrastructure.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("sucursales")
public class SucursalEntity {

	@Id
	private final Long id;
	private final String nombre;
	@Column(value = "id_franquicia")
	private final Long idFranquicia;
	
	public SucursalEntity(Long id, String nombre, Long idFranquicia) {
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
		return "SucursalEntity [id=" + id + ", nombre=" + nombre + ", idFranquicia=" + idFranquicia + "]";
	}
}
