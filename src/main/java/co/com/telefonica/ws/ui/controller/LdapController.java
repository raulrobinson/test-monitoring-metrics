package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.services.AppService;
import co.com.telefonica.ws.ui.dto.ldap.TelcoResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication Resource Management", description = "API Ldap-AD. validar el usuario ante el LDAP-AD de Telefonica.")
public class LdapController {

    private final AppService appService;

    /**
     * Instantiates a new Captcha controller.
     *
     * @param appService the app service
     */
    @Autowired
    public LdapController(AppService appService) {
        this.appService = appService;
    }

    /**
     * VALIDATE USER WITH LDAP-AD SERVICE
     * @param username Encrypt-data
     * @param password Encrypt-data
     * @return TelcoResponseDTO
     */
    @GetMapping(path = "/ldap", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TelcoResponseDTO> ldapByUsernameAndPassword(@RequestParam(name = "username") String username,
                                                                      @RequestParam(name = "password") String password) {
        var req = appService.ldapByUsernameAndPassword(username, password);
        if (req.getStatusCode().is5xxServerError()) {
            return new ResponseEntity<>(req.getBody(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(req.getBody(), HttpStatus.OK);
    }
}
