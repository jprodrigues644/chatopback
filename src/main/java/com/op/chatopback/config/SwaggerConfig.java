package com.op.chatopback.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration for API documentation.
 * <p>
 * Sets up OpenAPI with security schemes and API metadata.
 * </p>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI chatopOpenAPI() {

        return new OpenAPI()
                
                .info(new Info()
                        .title("Chatop API")
                        .description("API de gestion des locations (rentals)")
                        .version("1.0.0")
                )              
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                ); }
}