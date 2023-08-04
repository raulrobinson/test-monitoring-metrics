package co.com.telefonica.ws.ui.dto.ldap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelcoResponseDTO {
    private String timestamp;
    private Long code;
    private String message;
    private TelcoServiceResponseDTO serviceResponse;
}
