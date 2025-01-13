package com.backend.reactivo.app.infrastructure.adapters;

import org.springframework.stereotype.Repository;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.domain.ports.out.FranquiciaRepositoryPort;
import com.backend.reactivo.app.infrastructure.entities.FranquiciaEntity;
import com.backend.reactivo.app.infrastructure.mappers.FranquiciaMapper;
import com.backend.reactivo.app.infrastructure.repositories.FranquiciaEntityRepository;

import reactor.core.publisher.Mono;

@Repository
public class FranquiciaEntityRepositoryAdapter implements FranquiciaRepositoryPort {

	private final FranquiciaEntityRepository franquiciaEntityRepository;

	public FranquiciaEntityRepositoryAdapter(FranquiciaEntityRepository franquiciaEntityRepository) {
		this.franquiciaEntityRepository = franquiciaEntityRepository;
	}

	@Override
	public Mono<Franquicia> save(Franquicia franquicia) {

		FranquiciaEntity franquiciaEntity = FranquiciaMapper.toEntity(franquicia);

		return franquiciaEntityRepository.save(franquiciaEntity)
				.map(FranquiciaMapper::toDomain);

	}

}
