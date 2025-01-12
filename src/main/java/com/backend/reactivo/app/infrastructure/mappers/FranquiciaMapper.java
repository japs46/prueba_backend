package com.backend.reactivo.app.infrastructure.mappers;

import com.backend.reactivo.app.domain.model.Franquicia;
import com.backend.reactivo.app.infrastructure.entities.FranquiciaEntity;

public class FranquiciaMapper {

	public static Franquicia toDomain(FranquiciaEntity entity) {
        return new Franquicia(entity.getId(),entity.getNombre());
    }

    public static FranquiciaEntity toEntity(Franquicia model) {
        return new FranquiciaEntity(model.getId()!=null?model.getId():null, model.getNombre());
    }
	
}
