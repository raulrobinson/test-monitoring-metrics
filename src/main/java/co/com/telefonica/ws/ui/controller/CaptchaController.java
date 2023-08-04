package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.services.AppService;
import co.com.telefonica.ws.ui.dto.UCaptchaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Captcha controller.
 */
@Slf4j
@RestController
@RequestMapping(path = "${controller.properties.base-path}" + "/captcha", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Captcha", description = "API Captcha. Contiene las operacions para obtener y validar un Captcha.")
public class CaptchaController {

    private final AppService appService;

    /**
     * Instantiates a new Captcha controller.
     *
     * @param appService the app service
     */
    @Autowired
    public CaptchaController(AppService appService) {
        this.appService = appService;
    }

    /**
     * Gets captcha generate.
     *
     * @param servletRequest the servlet request
     * @return the captcha generates
     */
    @Operation(summary = "Generar Captcha", description = "Genera un Captcha para ser resuelto por un humano.")
    @PostMapping("generate")
    public ResponseEntity<UCaptchaResponseDto> getCaptchaGenerate(HttpServletRequest servletRequest) {
        var req = appService.getGenerateCaptcha(servletRequest);
        if (req.getStatusCode().is5xxServerError()) {
            return new ResponseEntity<>(req.getBody(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(req.getBody(), HttpStatus.OK);
    }

    /**
     * VALIDATE A CAPTCHA
     *
     * @param code           String
     * @param space          String
     * @param servletRequest the servlet request
     * @return ResponseCaptchaDTO captcha validate
     */
    @Operation(summary = "Validar Captcha", description = "Valida el Captcha generado mediante su vector Space y el Code resultado de Base64 to image.")
    @PostMapping(path = "validate")
    public ResponseEntity<UCaptchaResponseDto> getCaptchaValidate(
            @RequestParam String code,
            @RequestParam String space,
            HttpServletRequest servletRequest
    ) {
        try {
            return new ResponseEntity<>(appService
                    .getCaptchaValidate(servletRequest, code, space).getBody(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
