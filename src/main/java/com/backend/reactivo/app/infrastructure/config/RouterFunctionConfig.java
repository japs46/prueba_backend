package com.backend.reactivo.app.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.infrastructure.handler.FranquiciaHandler;
import com.backend.reactivo.app.infrastructure.handler.ProductoHandler;
import com.backend.reactivo.app.infrastructure.handler.SucursalHandler;

@Configuration
public class RouterFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> routesFranquicia(FranquiciaHandler franquiciaHandler){
		return RouterFunctions.route(POST("/api/franquicia"), franquiciaHandler::create);
	}
	
	@Bean
	public RouterFunction<ServerResponse> routesSucursal(SucursalHandler sucursalHandler){
		return RouterFunctions.route(POST("/api/sucursal"), sucursalHandler::create);
	}
	
	@Bean
	public RouterFunction<ServerResponse> routesProducto(ProductoHandler productoHandler){
		return RouterFunctions.route(POST("/api/producto"), productoHandler::create)
				.andRoute(DELETE("/api/producto/{id}"), productoHandler::delete)
				.andRoute(PUT("/api/producto/update-stock/{id}"), productoHandler::updateStock)
				.andRoute(GET("/api/producto/mayor-stock/franquicia/{franquiciaId}"),
						productoHandler::getProductosConMayorStockPorFranquicia);
	}

}
