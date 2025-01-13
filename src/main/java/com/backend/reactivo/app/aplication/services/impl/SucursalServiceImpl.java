package com.backend.reactivo.app.aplication.services.impl;

import org.springframework.stereotype.Service;

import com.backend.reactivo.app.aplication.services.SucursalService;
import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.CreateSucursalUseCase;
import com.backend.reactivo.app.domain.ports.in.UpdateSucursalUseCase;

import reactor.core.publisher.Mono;

@Service
public class SucursalServiceImpl implements SucursalService{
	
	private final CreateSucursalUseCase createSucursalUseCase;
	private final UpdateSucursalUseCase updateSucursalUseCase;
	
	public SucursalServiceImpl(CreateSucursalUseCase createSucursalUseCase,
			UpdateSucursalUseCase updateSucursalUseCase) {
		this.createSucursalUseCase = createSucursalUseCase;
		this.updateSucursalUseCase = updateSucursalUseCase;
	}

	@Override
	public Mono<Sucursal> save(Sucursal sucursal) {
		return createSucursalUseCase.save(sucursal);
	}

	@Override
	public Mono<Sucursal> updateNombre(Long id, String nombre) {
		return updateSucursalUseCase.updateNombre(id, nombre);
	}

}
