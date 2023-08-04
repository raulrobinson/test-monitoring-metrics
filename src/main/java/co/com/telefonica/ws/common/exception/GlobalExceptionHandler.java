package co.com.telefonica.ws.common.exception;

import co.com.telefonica.ws.common.utils.SecurityUtils;
import co.com.telefonica.ws.common.utils.Utilities;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webjars.NotFoundException;

import java.io.IOException;

/**
 * The type Global exception handler.
 *
 * @version 2.1.0
 * @autor: COE -Arquitectura-Telefonica
 * @date: 06 -06-2023
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleException(Exception ex,
                                                            HttpServletRequest request) {
        return new ResponseEntity<>(ExceptionMessage.builder()
                .timestamp(Utilities.getTimestampValue())
                .message(SecurityUtils.blindStr("Ocurrió un error en el servicio: " + ex.getMessage()))
                .path(request.getRequestURI())
                .build(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle null pointer exception response entity.
     *
     * @param request the request
     * @param ex      the ex
     * @return the response entity
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleNullPointerException(HttpServletRequest request, NullPointerException ex) {
        return new ResponseEntity<>(ExceptionMessage.builder()
                .timestamp(Utilities.getTimestampValue())
                .message(SecurityUtils.blindStr("Error general del Servicio: " + ex.getMessage()))
                .path(request.getRequestURI())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle not found exception response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleNotFoundException(HttpServletRequest request) {
        return new ResponseEntity<>(ExceptionMessage.builder()
                .timestamp(Utilities.getTimestampValue())
                .message(SecurityUtils.blindStr("El recurso solicitado no fue encontrado: " + request.getQueryString()))
                .path(request.getRequestURI())
                .build(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle io exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleIOException(IOException ex,
                                                              HttpServletRequest request) {
        return new ResponseEntity<>(ExceptionMessage.builder()
                .timestamp(Utilities.getTimestampValue())
                .message(SecurityUtils.blindStr("Error de headers: " + ex.getMessage()))
                .path(request.getRequestURI())
                .build(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle feign exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionMessage> handleFeignException(FeignException ex,
                                                                 HttpServletRequest request) {
        int statusCode = ex.status();
        return switch (statusCode) {
            case 404 -> new ResponseEntity<>(ExceptionMessage.builder()
                    .timestamp(Utilities.getTimestampValue())
                    .message(SecurityUtils.blindStr("Recurso no encontrado " + ex.getMessage()))
                    .path(request.getRequestURI())
                    .build(), HttpStatus.NOT_FOUND);
            case 400 -> new ResponseEntity<>(ExceptionMessage.builder()
                    .timestamp(Utilities.getTimestampValue())
                    .message(SecurityUtils.blindStr("Solicitud incorrecta: " + ex.getMessage()))
                    .path(request.getRequestURI())
                    .build(), HttpStatus.BAD_REQUEST);
            case 500 -> new ResponseEntity<>(ExceptionMessage.builder()
                    .timestamp(Utilities.getTimestampValue())
                    .message(SecurityUtils.blindStr("Error en la Solicitud " + ex.getMessage()))
                    .path(request.getRequestURI())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            default -> new ResponseEntity<>(ExceptionMessage.builder()
                    .timestamp(Utilities.getTimestampValue())
                    .message(SecurityUtils.blindStr("Error en la llamada al servicio " + ex.getMessage()))
                    .path(request.getRequestURI())
                    .build(), HttpStatus.BAD_GATEWAY);
        };
    }

}
