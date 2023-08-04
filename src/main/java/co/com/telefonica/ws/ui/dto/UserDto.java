package co.com.telefonica.ws.ui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private String password;
    private String role;
    private String email;
}
