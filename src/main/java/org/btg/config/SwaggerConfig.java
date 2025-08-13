package org.btg.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(tags = {
    @Tag(name = "Pedido", description = "Buscar e cadastrar pedidos") }, security = {
        @SecurityRequirement(name = "bearerToken")
    }, info = @Info(title = "API para consultar e realizar registro de pedidos", version = "1.0.0", contact = @Contact(name = "Thiago Isikawa", email = "thiago_hyd@hotmail.com")))
@SecurityScheme(securitySchemeName = "bearerToken", apiKeyName = "Authorization", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.APIKEY)
public class SwaggerConfig extends Application {

}
