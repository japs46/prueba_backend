package com.backend.reactivo.app.infrastructure.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.reactivo.app.infrastructure.entities.SucursalEntity;

@Repository
public interface SucursalEntityRepository extends ReactiveCrudRepository<SucursalEntity, Long>{

}
