package com.backend.reactivo.app.aplication.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.in.UpdateSucursalUseCase;
import com.backend.reactivo.app.domain.ports.out.SucursalRepositoryPort;

import reactor.core.publisher.Mono;

@Component
public class UpdateSucursalUseCaseImpl implements UpdateSucursalUseCase {

	private final SucursalRepositoryPort sucursalRepositoryPort;

	public UpdateSucursalUseCaseImpl(SucursalRepositoryPort sucursalRepositoryPort) {
		this.sucursalRepositoryPort = sucursalRepositoryPort;
	}

	@Override
	public Mono<Sucursal> updateNombre(Long id, String nombre) {
		return sucursalRepositoryPort.findById(id)
	            .switchIfEmpty(Mono.error(new IllegalArgumentException("Sucursal no encontrado con id: " + id)))
	            .flatMap(sucursal -> {
	                Sucursal updateSucursal = new Sucursal(sucursal.getId(),nombre,sucursal.getIdFranquicia());
	                return sucursalRepositoryPort.save(updateSucursal);
	            });
	}

}
