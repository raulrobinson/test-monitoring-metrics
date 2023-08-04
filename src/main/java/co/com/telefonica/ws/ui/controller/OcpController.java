package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.services.AppService;
import co.com.telefonica.ws.ui.dto.U3ScaleResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Ocp controller.
 */
@Slf4j
@RestController
@RequestMapping(path = "${controller.properties.base-path}" + "/ocp", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Utilities", description = "API Utilidades. Contiene las operaciones para obtener tokens de OCP, en Desarrollo o Produccion.")
public class OcpController {

    private final AppService appService;

    /**
     * Instantiates a new Product controller.
     *
     * @param appService the app service
     */
    @Autowired
    public OcpController(AppService appService) {
        this.appService = appService;
    }

    /**
     * Gets token ocp desa.
     *
     * @return the token ocp desa
     */
    @GetMapping(path = "/get-token-ocp-desa")
    public ResponseEntity<U3ScaleResponseDto> getTokenOcpDesa() {
        var req = appService.getTokenOcp();
        if (req.getStatusCode().is5xxServerError()) {
            return new ResponseEntity<>(req.getBody(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(req.getBody(), HttpStatus.OK);
    }

    /**
     * Gets token ocp prod.
     *
     * @return the token ocp prod
     */
    @GetMapping(path = "/get-token-ocp-prod")
    public ResponseEntity<U3ScaleResponseDto> getTokenOcpProd() {
        var req = appService.getTokenProdOcp();
        if (req.getStatusCode().is5xxServerError()) {
            return new ResponseEntity<>(req.getBody(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(req.getBody(), HttpStatus.OK);
    }
}
