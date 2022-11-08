package com.mianeko.fuzzyinferencesystemsbackend.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.ServletContext

@Configuration
class SwaggerConfig {
    private val url: String = System.getenv("URL")
    @Bean
    fun openAPI(servletContext: ServletContext): OpenAPI {
        return OpenAPI().addServersItem(Server().url(url))
            .info(
                Info()
                .title("Fuzzy Inference Systems Backend API")
                .version("3.0.0")
                .description("FuzzyInferenceSystemsBackend - API Swagger documentation")
            )
    }
}