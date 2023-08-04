package co.com.telefonica.ws.common.config.security;

import co.com.telefonica.ws.common.utils.ValidateHeaders;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * *********************************
 * **** NO BORRAR, NO MODIFICAR ****
 * *********************************
 * Description: Utilidades para validar los headers antes de ingresar a los
 *              Controladores o Resources del servicio.
 *
 * @version 2.1.0
 * @autor:  COE-Arquitectura-Telefonica
 * @date:   06-06-2023
 */
@Slf4j
@Configuration
public class HeaderFilter implements Filter {

    @Value("${controller.properties.base-path}")
    private String urlBase;

    @Value("${springdoc.openapi.auth-url}")
    private String authUrl;

    @Value("${springdoc.openapi.dev-url}")
    private String devUrl;

    private static final String SWAGGER_URL = "/swagger";
    private static final String API_DOCS = "/v3/api-docs";
    private static final String ACTUATOR = "/actuator";
    private static final String H2 = "/h2-console";

    /**
     * Description: Filtro para validar los headers del request con interceptores.
     *
     * @param servletRequest servletRequest
     * @param servletResponse servletResponse
     * @param filterChain filterChain
     * @throws IOException java.io. i o exception
     * @throws ServletException javax.servlet. servlet exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String methodName = String.valueOf(request.getRequestURL());

        // Exclusion para el login JWT de OpenApi.
        String loginUrl = devUrl + authUrl + "/login";
        if (loginUrl.equals(methodName)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Exclusion para el login antes el LDAP de Telefonica.
        String ldapUrl = devUrl + urlBase + "/ldap";
        if (ldapUrl.equals(methodName)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Exclusion para obtener un token de Desarrollo.
        String ocpDesaUrl = devUrl + urlBase + "/ocp/get-token-ocp-desa";
        if (ocpDesaUrl.equals(methodName)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Exclusion para obtener un token de Desarrollo.
        String ocpProdUrl = devUrl + urlBase + "/ocp/get-token-ocp-prod";
        if (ocpProdUrl.equals(methodName)) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(SWAGGER_URL) ||
            requestURI.startsWith(API_DOCS) ||
            requestURI.startsWith(ACTUATOR) ||
            requestURI.startsWith(H2)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        String system = request.getHeader("system");
        String operation = request.getHeader("operation");
        String msgType = request.getHeader("msgType");
        String paramAu = request.getHeader("paramAu");
        String execId = request.getHeader("execId");

        if (authorization == null || authorization.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el encabezado requerido: Authorization");
            log.error("Falta el encabezado requerido: Authorization");
            return;
        }


        if (system == null || system.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el encabezado requerido: system");
            log.error("Falta el encabezado requerido: system");
            return;
        }


        if (operation == null || operation.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el encabezado requerido: operation");
            log.error("Falta el encabezado requerido: operation");
            return;
        }


        if (msgType == null || msgType.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el encabezado requerido: msgType");
            log.error("Falta el encabezado requerido: msgType");
            return;
        }

        if (paramAu == null || paramAu.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el encabezado requerido: paramAu");
            log.error("Falta el encabezado requerido: paramAu");
            return;
        }


        if (execId == null || execId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el encabezado requerido: execId");
            log.error("Falta el encabezado requerido: execId");
            return;
        } else if (ValidateHeaders.validateRegExExecId(execId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato incorrecto para execId: [8]-[4]-[4]-[4]-[12]");
            log.error("El encabezado: execId presenta un formato incorrecto");
            return;
        }

        filterChain.doFilter(request, response);
    }

}

