package com.backend.reactivo.app.infrastructure.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.reactivo.app.domain.model.ProductoSucursal;
import com.backend.reactivo.app.infrastructure.entities.ProductoEntity;

import reactor.core.publisher.Flux;

@Repository
public interface ProductoEntityRepository extends ReactiveCrudRepository<ProductoEntity, Long> {

	@Query("""
			    SELECT
			        p.id AS producto_id,
			        p.nombre AS producto_nombre,
			        p.stock AS producto_stock,
			        s.id AS sucursal_id,
			        s.nombre AS sucursal_nombre
			    FROM Productos p
			    JOIN Sucursales s ON p.id_sucursal = s.id
			    JOIN Franquicias f ON s.id_franquicia = f.id
			    WHERE f.id = :franquiciaId
			      AND p.stock = (
			          SELECT MAX(p2.stock)
			          FROM Productos p2
			          WHERE p2.id_sucursal = s.id
			      )
			""")
	public Flux<ProductoSucursal> findProductoConMayorStockPorFranquicia(Long franquiciaId);

}
