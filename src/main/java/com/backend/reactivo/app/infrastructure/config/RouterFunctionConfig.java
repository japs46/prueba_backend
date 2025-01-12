package com.backend.reactivo.app.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.backend.reactivo.app.infrastructure.handler.FranquiciaHandler;

@Configuration
public class RouterFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> routes(FranquiciaHandler franquiciaHandler){
		return RouterFunctions.route(POST("/api/franquicia"), franquiciaHandler::create);
	}

}
