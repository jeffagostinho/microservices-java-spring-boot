package br.com.jeffagostinho.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    @Lazy(false)
    public List<org.springdoc.core.models.GroupedOpenApi> apis(
            org.springdoc.core.properties.SwaggerUiConfigParameters config,
            RouteDefinitionLocator locator) {

        var definitions = locator.getRouteDefinitions().collectList().block();

        assert definitions != null;

        definitions.stream().filter(
                        routeDefinition -> routeDefinition.getId()
                                .matches(".*-service"))
                .forEach(routeDefinition -> {
                            String name = routeDefinition.getId();
                            config.addGroup(name);
                            org.springdoc.core.models.GroupedOpenApi.builder()
                                    .pathsToMatch("/" + name + "/**")
                                    .group(name).build();
                        }
                );

        return new ArrayList<>();
    }

//    @Bean
//    @Lazy(false)
//    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters config, RouteDefinitionLocator locator) {
//        var definitions = locator.getRouteDefinitions().collectList().block();
//        assert definitions != null;
//
//        List<GroupedOpenApi> groups = new ArrayList<>();
//
//        definitions.stream()
//                .filter(route -> route.getId().endsWith("-service"))
//                .forEach(route -> {
//                    String name = route.getId();
//                    config.addGroup(name);
//                    groups.add(GroupedOpenApi.builder()
//                            .group(name)
//                            .pathsToMatch("/" + name + "/**")
//                            .build());
//                });
//
//        return groups;
//    }
}
