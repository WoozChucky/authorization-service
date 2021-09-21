package xyz.nunolevezinho.authorization.config;

import com.google.common.net.HttpHeaders;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("xyz.nunolevezinho.authorization"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(Collections.singletonList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
            .securityContexts(Collections.singletonList(securityContext()))
            .apiInfo(
                new ApiInfoBuilder()
                    .version("1.0.0")
                    .title("Authorization Service API")
                    .description("Documentation API v1.0")
                    .contact(new Contact("Development Team", "", "email@example.com"))
                    .build()
            );
    }

    public static final String TOKEN_ACCESS = "Token Access";

    public static SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultUser())
            .forPaths(PathSelectors.any())
            .build();
    }

    public static List<SecurityReference> defaultUser() {
        return Collections.singletonList(
            new SecurityReference(TOKEN_ACCESS, new AuthorizationScope[]{
                new AuthorizationScope("USER", "")
            }));
    }

}
