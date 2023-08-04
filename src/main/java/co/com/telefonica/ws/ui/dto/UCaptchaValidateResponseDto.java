package co.com.telefonica.ws.ui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UCaptchaValidateResponseDto {

    private String code;
    private String space;

}
