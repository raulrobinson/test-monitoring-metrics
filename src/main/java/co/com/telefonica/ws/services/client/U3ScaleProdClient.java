package co.com.telefonica.ws.services.client;

import co.com.telefonica.ws.ui.dto.U3ScaleOcpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "util-token-prod-ocp", url = "${prod.access.three.scale.url}")
public interface U3ScaleProdClient {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    U3ScaleOcpResponse getTokenProdOcp(@RequestParam(name = "channel") String channel,
                                       @RequestParam(name = "grant_type") String grantType,
                                       @RequestParam(name = "client_id") String clientId,
                                       @RequestParam(name = "client_secret") String clientSecret,
                                       @RequestParam(name = "scope") String scope);
}
