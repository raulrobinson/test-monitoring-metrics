package co.com.telefonica.ws.ui.dto;

import co.com.telefonica.ws.common.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

/**
 * ************************************
 * **** NO BORRAR, NO MODIFICAR!!! ****
 * ************************************
 * RESPONSE DTO TELEFONICA - MOVISTAR.
 * Author: Microservices Governance
 * By: Arquitectura - @Telefonica 2023
 * Version: 2.0.0
 */
@Data
@Builder
public class ResponseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_FORMAT)
    private String timestamp;
    private String message;
    private Object serviceResponse;
}
