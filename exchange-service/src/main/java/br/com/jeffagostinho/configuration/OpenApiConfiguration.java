package br.com.jeffagostinho.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info = @Info(
        title = "Exchange Service API",
        version = "v1",
        description = "API for managing exchanges"))
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Exchange Service API")
                        .version("v1")
                        .license(
                            new License()
                                    .name("Apache 2.0")
                                    .url("http://springdoc.org")
                        )
                        .description("API for managing exchanges"));
    }
}
