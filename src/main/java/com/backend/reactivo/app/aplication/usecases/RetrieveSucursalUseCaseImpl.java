package com.backend.reactivo.app.aplication.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.RetrieveSucursalUseCase;
import com.backend.reactivo.app.domain.ports.out.SucursalRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class RetrieveSucursalUseCaseImpl implements RetrieveSucursalUseCase{
	
	private final SucursalRepositoryPort sucursalRepositoryPort;
	
	public RetrieveSucursalUseCaseImpl(SucursalRepositoryPort sucursalRepositoryPort) {
		this.sucursalRepositoryPort = sucursalRepositoryPort;
	}

	@Override
	public Mono<Sucursal> findById(Long id) {
		return sucursalRepositoryPort.findById(id);
	}

}
