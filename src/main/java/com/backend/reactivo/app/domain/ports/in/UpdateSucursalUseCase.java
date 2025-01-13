package com.backend.reactivo.app.domain.ports.in;

import com.backend.reactivo.app.domain.model.Sucursal;

import reactor.core.publisher.Mono;

public interface UpdateSucursalUseCase {

	public Mono<Sucursal> updateNombre(Long id, String nombre);
}
