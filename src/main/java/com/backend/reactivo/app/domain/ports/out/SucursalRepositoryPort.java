package com.backend.reactivo.app.domain.ports.out;

import com.backend.reactivo.app.domain.model.Sucursal;

import reactor.core.publisher.Mono;

public interface SucursalRepositoryPort {

	public Mono<Sucursal> save(Sucursal sucursal);
}
