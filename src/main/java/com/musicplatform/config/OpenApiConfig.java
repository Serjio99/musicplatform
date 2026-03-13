package com.musicplatform.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI musicPlatformOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("MusicPlatform API")
                        .description("Backend API для музыкальной платформы")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MusicPlatform Team")
                                .email("dev@musicplatform.local")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project documentation")
                        .url("https://github.com/Serjio99/musicplatform"));
    }
}