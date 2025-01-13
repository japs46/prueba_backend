package com.backend.reactivo.app.infrastructure.mappers;

import com.backend.reactivo.app.domain.model.Producto;
import com.backend.reactivo.app.infrastructure.entities.ProductoEntity;

public class ProductoMapper {

	public static Producto toDomain(ProductoEntity entity) {
        return new Producto(entity.getId(),entity.getNombre(),entity.getStock(),entity.getIdSucursal());
    }

    public static ProductoEntity toEntity(Producto model) {
        return new ProductoEntity(model.getId()!=null?model.getId():null, model.getNombre(),model.getStock(),
        		model.getIdSucursal());
    }
}
