package co.com.telefonica.ws.ui.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UCaptchaResponseDto {

    private long error;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UCaptchaValues values;

}
