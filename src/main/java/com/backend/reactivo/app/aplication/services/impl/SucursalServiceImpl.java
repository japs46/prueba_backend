package com.backend.reactivo.app.aplication.services.impl;

import org.springframework.stereotype.Service;

import com.backend.reactivo.app.aplication.services.SucursalService;
import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.CreateSucursalUseCase;

import reactor.core.publisher.Mono;

@Service
public class SucursalServiceImpl implements SucursalService{
	
	private final CreateSucursalUseCase createSucursalUseCase;
	
	public SucursalServiceImpl(CreateSucursalUseCase createSucursalUseCase) {
		this.createSucursalUseCase = createSucursalUseCase;
	}

	@Override
	public Mono<Sucursal> save(Sucursal sucursal) {
		return createSucursalUseCase.save(sucursal);
	}

}
