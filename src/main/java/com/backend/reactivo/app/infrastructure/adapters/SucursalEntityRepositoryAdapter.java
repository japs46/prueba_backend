package com.backend.reactivo.app.infrastructure.adapters;

import org.springframework.stereotype.Repository;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.domain.ports.out.SucursalRepositoryPort;
import com.backend.reactivo.app.infrastructure.entities.SucursalEntity;
import com.backend.reactivo.app.infrastructure.mappers.SucursalMapper;
import com.backend.reactivo.app.infrastructure.repositories.SucursalEntityRepository;

import reactor.core.publisher.Mono;

@Repository
public class SucursalEntityRepositoryAdapter implements SucursalRepositoryPort{

	private final SucursalEntityRepository SucursalEntityRepository;

	public SucursalEntityRepositoryAdapter(
			com.backend.reactivo.app.infrastructure.repositories.SucursalEntityRepository sucursalEntityRepository) {
		SucursalEntityRepository = sucursalEntityRepository;
	}

	@Override
	public Mono<Sucursal> save(Sucursal sucursal) {
		SucursalEntity sucursalEntity = SucursalMapper.toEntity(sucursal);

		return SucursalEntityRepository.save(sucursalEntity)
				.map(SucursalMapper::toDomain);
	}

}
