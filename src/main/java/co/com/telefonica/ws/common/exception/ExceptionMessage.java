package co.com.telefonica.ws.common.exception;

import co.com.telefonica.ws.common.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

/**
 * The type Exception message.
 *
 * @autor:  COE-Arquitectura-Telefonica
 * @date:   06-06-2023
 * @version 2.1.0
 */
@Data
@Builder
public class ExceptionMessage {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_FORMAT)
    private String timestamp;
    private String message;
    private String path;

}
