package co.com.telefonica.ws.ui.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UProductDto{
	private String code;
	private List<UProductItemDto> serviceResponse;
	private String type;
	private String message;
}
