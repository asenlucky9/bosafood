package com.bosafood.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class OpenApiConfig {
    
    @Value("${server.port:8081}")
    private String serverPort;
    
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server()
                .url("http://localhost:" + serverPort)
                .description("Development server");

        Info info = new Info()
                .title("Bosa Kitchen API")
                .version("1.0.0")
                .description("REST API for Bosa Kitchen - Authentic African Restaurant");

        return new OpenAPI()
                .info(info)
                .addServersItem(server);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("bosa-kitchen")
                .packagesToScan("com.bosafood.controller")
                .pathsToMatch("/api/**")
                .build();
    }
} 