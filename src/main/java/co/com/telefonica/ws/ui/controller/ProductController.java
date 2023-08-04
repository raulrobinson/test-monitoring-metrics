package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.services.AppService;
import co.com.telefonica.ws.ui.dto.UProductResponseDto;
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
 * The type Product controller.
 */
@Slf4j
@RestController
@RequestMapping(path = "${controller.properties.base-path}" + "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Productos", description = "API Productos. Contiene la operacion para obtener los productos de un Cliente.")
public class ProductController {

    private final AppService appService;

    /**
     * Instantiates a new Product controller.
     *
     * @param appService the app service
     */
    @Autowired
    public ProductController(AppService appService) {
        this.appService = appService;
    }

    /**
     * Gets product by id type and id number.
     *
     * @param idType         the id type
     * @param idNumber       the id number
     * @param servletRequest the servlet request
     * @return the product by id type and id number
     */
    @Operation(summary = "Listado de Productos", description = "Obtiene los productos de un Cliente.")
    @PostMapping("product")
    public ResponseEntity<UProductResponseDto> getProductByIdTypeAndIdNumber(@RequestParam(name = "idType") String idType,
                                                                             @RequestParam(name = "idNumber") String idNumber,
                                                                             HttpServletRequest servletRequest) {
        var req = appService.getProductCustomer(servletRequest, idType, idNumber);
        if (req.getStatusCode().is5xxServerError()) {
            return new ResponseEntity<>(req.getBody(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(req.getBody(), HttpStatus.OK);
    }
}
