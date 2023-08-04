package co.com.telefonica.ws.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * The type Set headers.
 */
@Slf4j
@Component
public class SetHeaders {

    private final HttpHeaders headersCustomer = new HttpHeaders();

    /**
     * Header for customer http headers.
     *
     * @param request the request
     * @return the http headers
     */
    public HttpHeaders headerForCustomer(HttpServletRequest request) {
        headersCustomer.add(Constants.SYSTEM, request.getHeader(Constants.SYSTEM).trim());
        headersCustomer.add(Constants.SUBSYSTEM, request.getHeader(Constants.SUBSYSTEM).trim());
        headersCustomer.add(Constants.USER_ID, request.getHeader(Constants.USER_ID).trim());
        headersCustomer.add(Constants.OPERATION, request.getHeader(Constants.OPERATION).trim());
        headersCustomer.add(Constants.ORIGINATOR, request.getHeader(Constants.ORIGINATOR).trim());
        headersCustomer.add(Constants.DESTINATION, request.getHeader(Constants.DESTINATION).trim());
        headersCustomer.add(Constants.COUNTRY, request.getHeader(Constants.COUNTRY).trim());
        headersCustomer.add(Constants.LANG, request.getHeader(Constants.LANG).trim());
        headersCustomer.add(Constants.EXECID, request.getHeader(Constants.EXECID).trim());
        headersCustomer.add(Constants.ENTITY, request.getHeader(Constants.ENTITY).trim());
        headersCustomer.add(Constants.TIMESTAMP, Utilities.clientDateStringWithTZ());
        headersCustomer.add(Constants.MSGTYPE, request.getHeader(Constants.MSGTYPE).trim());
        headersCustomer.add(Constants.VARARG, request.getHeader(Constants.VARARG).trim());
        headersCustomer.add(Constants.APPID, request.getHeader(Constants.APPID).trim());

        return headersCustomer;
    }

}
