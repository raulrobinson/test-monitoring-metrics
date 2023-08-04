package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.common.utils.Constants;
import co.com.telefonica.ws.common.utils.SecurityUtils;
import co.com.telefonica.ws.common.utils.Utilities;
import co.com.telefonica.ws.common.utils.ValidateHeaders;
import co.com.telefonica.ws.services.AppService;
import co.com.telefonica.ws.ui.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Description: Metodo Clase Controller del modelo MVC.
 *
 * @version 2.1.0
 * @autor: COE -Arquitectura-Telefonica
 * @date: 06 -06-2023
 */
@Slf4j
@RestController
@RequestMapping(path = "${controller.properties.base-path}" + "/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Locations", description = "API Geografica. Contiene las operacions para obtener las distintas Ciudades y Municipios de Colombia.")
public class LocationController {

    private final AppService appService;

    /**
     * Instantiates a new Locations controller.
     *
     * @param appService the app service
     */
    @Autowired
    public LocationController(AppService appService) {
        this.appService = appService;
    }

    /**
     * Gets all locations.
     *
     * @param headers the headers
     * @return the all locations
     */
    @Operation(summary = "Listado de Todas las Ciudades y Municipios", description = "Obtiene todas las Ciudades y Municipios de Colombia.")
    @GetMapping("all-locations")
    public ResponseEntity<ResponseDto> getAllLocations(final @RequestHeader HttpHeaders headers) {

        headers.add(Constants.TIMESTAMP, Utilities.getTimestampValue());

        var req = appService.getAllLocations();
        var res = SecurityUtils.blindLocationsList(Objects.requireNonNull(req.getBody()));

        if (req.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(SecurityUtils.blindStr(Utilities.getTimestampValue()))
                    .message(SecurityUtils.blindStr(String.valueOf(req.getStatusCode())))
                    .serviceResponse(res)
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Gets locations by id.
     *
     * @param id      the id
     * @param headers the headers
     * @return the locations by id
     */
    @Operation(summary = "Encuentra un Ciudad y Municipio", description = "Obtiene una Ciudad o Municipio a partir de su Id.")
    @GetMapping("/id")
    public ResponseEntity<ResponseDto> getLocationsById(final @RequestParam(value = "id") Long id,
                                                        final @RequestHeader HttpHeaders headers) {

        headers.add(Constants.TIMESTAMP, ValidateHeaders.getTimestampValue());

        var req = appService.findById(SecurityUtils.blindParameterLong(id));
        var res = SecurityUtils.blindLocations(Objects.requireNonNull(req.getBody()));

        if (req.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(SecurityUtils.blindStr(Utilities.getTimestampValue()))
                    .message(SecurityUtils.blindStr(String.valueOf(req.getStatusCode())))
                    .serviceResponse(res)
                    .build(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    /**
     * Gets locations by municipality.
     *
     * @param headers      the headers
     * @param municipality the municipality
     * @return the locations by municipality
     */
    @Operation(summary = "Listado de Ciudades o Municipios", description = "Obtiene Ciudades y Municipios de Colombia.")
    @GetMapping("/municipalities")
    public ResponseEntity<ResponseDto> getLocationsByMunicipality(final @RequestHeader HttpHeaders headers,
                                                                  final @RequestParam(value = "municipality") String municipality) {

        headers.add(Constants.TIMESTAMP, ValidateHeaders.getTimestampValue());

        var req = appService.getLocationByMunicipality(SecurityUtils.blindStr(municipality));
        var res = SecurityUtils.blindLocationsList(Objects.requireNonNull(req.getBody()));

        if (req.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(SecurityUtils.blindStr(Utilities.getTimestampValue()))
                    .message(SecurityUtils.blindStr(String.valueOf(req.getStatusCode())))
                    .serviceResponse(res)
                    .build(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    /**
     * Gets locations by department.
     *
     * @param headers    the headers
     * @param department the department
     * @return the locations by department
     */
    @Operation(summary = "Listado de Departamentos", description = "Obtiene todas los Departamentos de Colombia.")
    @GetMapping("/departaments")
    public ResponseEntity<ResponseDto> getLocationsByDepartment(final @RequestHeader HttpHeaders headers,
                                                                final @RequestParam(value = "department") String department) {

        headers.add(Constants.TIMESTAMP, ValidateHeaders.getTimestampValue());

        var req = appService.getLocationByDepartment(SecurityUtils.blindStr(department));
        var res = SecurityUtils.blindLocationsList(Objects.requireNonNull(req.getBody()));

        if (req.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(SecurityUtils.blindStr(Utilities.getTimestampValue()))
                    .message(SecurityUtils.blindStr(String.valueOf(req.getStatusCode())))
                    .serviceResponse(res)
                    .build(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    /**
     * Gets locations by region.
     *
     * @param headers the headers
     * @param region  the region
     * @return the locations by region
     */
    @Operation(summary = "Listado de Regiones", description = "Obtiene todas las Regiones de Colombia.")
    @GetMapping("/regions")
    public ResponseEntity<ResponseDto> getLocationsByRegion(final @RequestHeader HttpHeaders headers,
                                                            final @RequestParam(value = "region") String region) {

        headers.add(Constants.TIMESTAMP, ValidateHeaders.getTimestampValue());

        var req = appService.getLocationByRegion(SecurityUtils.blindStr(region));
        var res = SecurityUtils.blindLocationsList(Objects.requireNonNull(req.getBody()));

        if (req.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(SecurityUtils.blindStr(Utilities.getTimestampValue()))
                    .message(SecurityUtils.blindStr(String.valueOf(req.getStatusCode())))
                    .serviceResponse(res)
                    .build(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

}
