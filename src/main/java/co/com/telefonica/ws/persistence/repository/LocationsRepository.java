package co.com.telefonica.ws.persistence.repository;

import co.com.telefonica.ws.persistence.entity.Locations;
import co.com.telefonica.ws.ui.dto.LocationsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Long> {
    LocationsResponseDto getLocationByRegion(String region);
    LocationsResponseDto getLocationByDepartamento(String departamento);
    List<Locations> findByMunicipioContaining(String municipio);
    List<Locations> findByDepartamentoContaining(String departamento);
    List<Locations> findByRegionContaining(String region);
    List<Locations> findByCodigoDaneDelDepartamentoContaining(String departmentCode);
    List<Locations> findByCodigoDaneDelMunicipioContaining(String municipalityCode);
}
