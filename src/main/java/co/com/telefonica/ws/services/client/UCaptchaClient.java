package co.com.telefonica.ws.services.client;

import co.com.telefonica.ws.ui.dto.UCaptchaResponseDto;
import co.com.telefonica.ws.ui.dto.UCaptchaValidateResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * The interface U captcha client.
 */
@FeignClient(value = "util-captcha-ocp", url = "${ms.captcha.url}")
public interface UCaptchaClient {

    /**
     * Generate captcha response entity.
     *
     * @param httpHeaders the http headers
     * @return the response entity
     */
    @PostMapping(path = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UCaptchaResponseDto> generateCaptcha(@RequestHeader HttpHeaders httpHeaders);

    /**
     * Validate captcha response entity.
     *
     * @param httpHeaders            the http headers
     * @param requestValidateCaptcha the request validate captcha
     * @return the response entity
     */
    @PostMapping(path = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UCaptchaResponseDto> validateCaptcha(@RequestHeader HttpHeaders httpHeaders,
                                                        @RequestBody UCaptchaValidateResponseDto requestValidateCaptcha);

}
