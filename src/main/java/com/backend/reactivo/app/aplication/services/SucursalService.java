package com.backend.reactivo.app.aplication.services;

import com.backend.reactivo.app.domain.model.Sucursal;

import reactor.core.publisher.Mono;

public interface SucursalService {

	public Mono<Sucursal> save(Sucursal sucursal);
	
}
