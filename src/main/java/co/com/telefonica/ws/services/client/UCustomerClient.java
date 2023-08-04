package co.com.telefonica.ws.services.client;

import co.com.telefonica.ws.ui.dto.customer.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "util-customer-ocp", url = "${ms.customer-detail.url}")
public interface UCustomerClient {

    @GetMapping(path = "?idTypeNationalIdentityCardIdentification={idType}&APP_ID={appId}&APP_KEY={appKey}&passportNrPassportIdentification={idNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@RequestMapping(path = "?idTypeNationalIdentityCardIdentification={idType}&APP_ID={appId}&APP_KEY={appKey}&passportNrPassportIdentification={idNumber}", method = RequestMethod.GET, produces = "application/json")
    CustomerDto getCustomerDetail(@RequestHeader HttpHeaders httpHeaders,
                                  @PathVariable String idType,
                                  @PathVariable String idNumber,
                                  @PathVariable String appId,
                                  @PathVariable String appKey);
}
