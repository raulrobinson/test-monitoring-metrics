package co.com.telefonica.ws.services.client;

import co.com.telefonica.ws.ui.dto.U3ScaleOcpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface U 3 scale client.
 */
@FeignClient(value = "util-token-ocp", url = "${access.three.scale.url}")
public interface U3ScaleClient {

    /**
     * Gets token ocp.
     *
     * @param channel      the channel
     * @param grantType    the grant type
     * @param clientId     the client id
     * @param clientSecret the client secret
     * @param scope        the scope
     * @return the token ocp
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    U3ScaleOcpResponse getTokenOcp(@RequestParam(name = "channel") String channel,
                                   @RequestParam(name = "grant_type") String grantType,
                                   @RequestParam(name = "client_id") String clientId,
                                   @RequestParam(name = "client_secret") String clientSecret,
                                   @RequestParam(name = "scope") String scope);

}
