package co.com.telefonica.ws.ui.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationsRequestDto {
    private String region;
    private String departamento;
    private String municipio;
}
