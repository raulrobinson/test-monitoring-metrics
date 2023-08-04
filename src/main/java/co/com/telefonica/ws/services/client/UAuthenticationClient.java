package co.com.telefonica.ws.services.client;

import co.com.telefonica.ws.ui.dto.ldap.TelcoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "authentication-resource-management", url = "${ms.authentication-management.url}")
public interface UAuthenticationClient {

    @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    TelcoResponseDTO ldapByUsernameAndPassword(@RequestHeader HttpHeaders httpHeaders,
                                               @RequestParam(name = "username") String username,
                                               @RequestParam(name = "password") String password);

}
