package co.com.telefonica.ws.common.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * The type Open api config.
 */
@Configuration
public class OpenApiConfig {

    @Value("${springdoc.openapi.dev-url}")
    private String devUrl;

    private final OpenApiProperties openApiProperties;

    /**
     * Instantiates a new Open api config.
     *
     * @param openApiProperties the swagger properties
     */
    @Autowired
    public OpenApiConfig(OpenApiProperties openApiProperties) {
        this.openApiProperties = openApiProperties;
    }

    /**
     * My open api.
     *
     * @return the open api
     */
    @Bean
    public OpenAPI myOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription(this.openApiProperties.getProjectShortDescription());

        Contact contact = new Contact();
        contact.setEmail(this.openApiProperties.getDeveloperMail());
        contact.setName(this.openApiProperties.getDeveloperName());
        contact.setUrl(this.openApiProperties.getOrganizationUrl());

        License mitLicense = new License()
                .name("MIT License")
                .url(this.openApiProperties.getProjectLicenceLink());

        Info info = new Info()
                .title(this.openApiProperties.getProjectName())
                .version("1.0")
                .contact(contact)
                .description(this.openApiProperties.getProjectShortDescription())
                .termsOfService(this.openApiProperties.getProjectLicenceMsg())
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description(
                                        "Suministre el token JWT. El token JWT puedes generarlo desde la API Login. solo pruebas, utilice las credenciales <strong>admin/admin</strong>")
                                .bearerFormat("JWT")));
    }

    /**
     * Global parameter operation customizer operation customizer.
     *
     * @return the operation customizer
     */
    @Bean
    public OperationCustomizer globalParameterOperationCustomizer() {
        return (operation, handlerMethod) -> {
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();

            // excluye el controlador y el metodo de solicitud de headers en OpenApi.
            if ("OpenApiController".equals(controllerName) && "login".equals(methodName) ||
                "LdapController".equals(controllerName) && "ldapByUsernameAndPassword".equals(methodName) ||
                "OcpController".equals(controllerName) && "getTokenOcpDesa".equals(methodName) ||
                "OcpController".equals(controllerName) && "getTokenOcpProd".equals(methodName)) {
                return operation;
            }

            List<Parameter> globalParameters = Arrays.asList(
                    new Parameter()
                            .name("system")
                            .description("Sistema que realiza la solicitud. Ej: CoEArqSolucion")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("subsystem")
                            .description("Sub-Sistema que realiza la solicitud. Ej: Orchestrator")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("originator")
                            .description("Unidad que realiza la solicitud. Ej: originator")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("operation")
                            .description("Identificador de la operación ofrecida por el servicio. Ej: Entity")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("destination")
                            .description("Destino que consume el servicio. Ej: fullstack")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("execId")
                            .description("Identificador único de la petición. Ej: 550e8400-e29b-41d4-a716-446655440001")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("msgType")
                            .description("Tipo de mensaje, se relaciona con el escenario de uso. Ej: Request")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("paramAu")
                            .description("Parametro de Autorizacion en el Servicio. Ej: 1")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("varArg")
                            .description("Parametro Variable en el Servicio. Ej: 1")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("appId")
                            .description("Parametro Id de la App en el Servicio. Ej: 1")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("userId")
                            .description("User Id que genera el Servicio. Ej: 123")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("country")
                            .description("Codigo del pais del Servicio. Ej: CO")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("lang")
                            .description("Lenguage. Ej: ES-CO")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false),
                    new Parameter()
                            .name("entity")
                            .description("Entidad Ej: entity")
                            .in(ParameterIn.HEADER.toString())
                            .schema(new StringSchema())
                            .required(false)
            );

            globalParameters.forEach(operation::addParametersItem);

            return operation;
        };
    }
}
