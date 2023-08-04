package co.com.telefonica.ws.ui.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationsResponseDto {
    private Long id;
    private String region;
    private String codigoDaneDelDepartamento;
    private String departamento;
    private String codigoDaneDelMunicipio;
    private String municipio;
}
