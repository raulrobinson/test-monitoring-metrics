package co.com.telefonica.ws.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UCaptchaValues {

    @JsonProperty("Image")
    public String image;

    private String space;

}
