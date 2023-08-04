package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.persistence.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "${springdoc.openapi.auth-url}", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Auth OpenApi", description = "API de Autenticacion. Contiene la operacion autenticarse en Swagger al obtener un token JWT.")
public class OpenApiController {

    private final UserDetailsService userDetailsService;
    private final JwtEncoder encoder;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param userDetailsService the user details service
     * @param encoder            the encoder
     */
    public OpenApiController(UserDetailsService userDetailsService,
                             JwtEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    /**
     * Login response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @Operation(summary = "Autenticacion de Usuario", description = "Autenticar el usuario y retornar un token JWT para OpenAPI.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "{\n" + "  \"username\": \"admin\", \"password\": \"admin\"\n" + "}",
                            summary = "Ejemplo de Autenticacion de Usuario")))
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody User user) {

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUsername());

        if (user.getPassword().equalsIgnoreCase(userDetails.getPassword())) {
            String token = generateToken(userDetails);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-AUTH-TOKEN", token);

            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"token\":\"" + token + "\"}");
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Usuario o Password invalido");
        }
    }

    /**
     * generate token
     *
     * @param userDetails userDetails
     * @return {@link String}
     * @see String
     */
    private String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = userDetails
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(userDetails.getUsername())
                .claim("scope", scope)
                .build();

        return this.encoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
