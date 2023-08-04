package co.com.telefonica.ws.services.client;

import co.com.telefonica.ws.ui.dto.UProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * The interface U product client.
 */
@FeignClient(value = "util-product-ocp", url = "${ms.product-query-system.url}")
public interface UProductClient {

    /**
     * Gets a product list.
     *
     * @param headers  the headers
     * @param idType   the id type
     * @param idNumber the id number
     * @return the product list
     */
    @GetMapping(path = "/dni/{idType}/{idNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UProductDto> getProductList(
            @RequestHeader HttpHeaders headers,
            @PathVariable String idType,
            @PathVariable String idNumber);
}
