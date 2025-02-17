package com.guna.yumzoom.configration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Mr.Mathesh",
                        email = "mathesh1907@gmail.com",
                        url = "https://matheshrose.vercel.app/"
                ),
                description = "OpenAPI documentation for YumZoom Application",
                title = "OpenAPI Specification",
                version = "1.0",
                license = @License(
                        name = "Mr.Guna - Website",
                        url = "https://gunamurugesan.vercel.app/"

                ),
                termsOfService = "https://media.tenor.com/_4YgA77ExHEAAAAC/error-404.gif"
        ),
        servers = {
                @Server(
                        description = "local ENV",
                        url = "http://localhost:8080/api/v1"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://localhost:8080/api/v1"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {

}
