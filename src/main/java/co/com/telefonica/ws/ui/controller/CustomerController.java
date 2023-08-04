package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.services.AppService;
import co.com.telefonica.ws.ui.dto.customer.CustomerDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${controller.properties.base-path}" + "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Customer Detail", description = "API Customer Detail. consulta los datos de un cliente por su tipo de documento y numero de documento.")
public class CustomerController {

    private final AppService appService;

    /**
     * Instantiates a new Captcha controller.
     *
     * @param appService the app service
     */
    @Autowired
    public CustomerController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping(path = "/customer-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CustomerDto> getCustomerDetail(HttpServletRequest servletRequest,
                                                  @RequestParam(name = "idTypeNationalIdentityCardIdentification") String idType,
                                                  @RequestParam(name = "passportNrPassportIdentification") String idNumber) {
        var req = appService.getCustomerDetail(servletRequest, idType, idNumber);
        if (req.getStatusCode().is5xxServerError()) {
            return new ResponseEntity<>(req.getBody(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(req.getBody(), HttpStatus.OK);
    }

    @GetMapping(path = "/name-of-customer", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getCompleteNameOfCustomer(HttpServletRequest servletRequest,
                                                     @RequestParam(name = "idTypeNationalIdentityCardIdentification") String idType,
                                                     @RequestParam(name = "passportNrPassportIdentification") String idNumber) {
        var req = appService.getCompleteNameOfCustomer(servletRequest, idType, idNumber);
        if (req.getStatusCode().is5xxServerError()) {
            return new ResponseEntity<>(req.getBody(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(req.getBody(), HttpStatus.OK);
    }




}
