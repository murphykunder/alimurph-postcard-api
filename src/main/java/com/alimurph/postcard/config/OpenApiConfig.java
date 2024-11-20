package com.alimurph.postcard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * The OpenAPI specification lets us define a set of security schemes for the API.
 * We can configure the security requirements of the API globally or apply/remove them per endpoint.
 * http://localhost:8088/api/v1/swagger-ui/index.html
 */
@OpenAPIDefinition(
    info = @Info(
            contact = @Contact(
                    name = "AliMurph-Postcard"
            ),
            description = "OpenApi documentation for Alimurph-Postcard",
            title = "OpenApi Specification - AliMurph-Postcard",
            version = "1.0",
            termsOfService = "Terms of Service"
    ),
    servers = {
            @Server(
                    description = "Local environment",
                    url = "http://localhost:8088/api/v1"
            ),
    }
)
public class OpenApiConfig {
}
