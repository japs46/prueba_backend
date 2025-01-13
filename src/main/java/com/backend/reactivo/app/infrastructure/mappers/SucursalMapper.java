package com.backend.reactivo.app.infrastructure.mappers;

import com.backend.reactivo.app.domain.model.Sucursal;
import com.backend.reactivo.app.infrastructure.entities.SucursalEntity;

public class SucursalMapper {

	public static Sucursal toDomain(SucursalEntity entity) {
        return new Sucursal(entity.getId(),entity.getNombre(),entity.getIdFranquicia());
    }

    public static SucursalEntity toEntity(Sucursal model) {
        return new SucursalEntity(model.getId()!=null?model.getId():null, model.getNombre(),model.getIdFranquicia());
    }
}
