package co.com.telefonica.ws.ui.dto.ldap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelcoServiceResponseDTO {
    private Long code;
    private Message message;
    private String username;
}
