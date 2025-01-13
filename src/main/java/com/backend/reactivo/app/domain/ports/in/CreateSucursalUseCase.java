package com.backend.reactivo.app.domain.ports.in;

import com.backend.reactivo.app.domain.model.Sucursal;

import reactor.core.publisher.Mono;

public interface CreateSucursalUseCase {

	public Mono<Sucursal> save(Sucursal sucursal);
}
