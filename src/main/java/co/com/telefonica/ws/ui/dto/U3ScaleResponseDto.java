package co.com.telefonica.ws.ui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class U3ScaleResponseDto {
	private String expiresIn;
	private String token;
	private String refreshToken;
	private String refreshExpiresIn;
}