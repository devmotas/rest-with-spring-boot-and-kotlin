package br.com.erudio.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("RESTful API with Kotlin")
                    .version("1.0.0")
                    .description("Some description about API")
                    .termsOfService("https://pub.erudio.com.br/meus-cursos")
                    .license(
                        License().name("Apache 2.0")
                            .url("https://pub.erudio.com.br/meus-cursos")
                    )
            )
    }
}
