package co.com.telefonica.ws.services;

import co.com.telefonica.ws.persistence.entity.Locations;
import co.com.telefonica.ws.persistence.entity.User;
import co.com.telefonica.ws.ui.dto.U3ScaleResponseDto;
import co.com.telefonica.ws.ui.dto.UCaptchaResponseDto;
import co.com.telefonica.ws.ui.dto.UProductResponseDto;
import co.com.telefonica.ws.ui.dto.UserDto;
import co.com.telefonica.ws.ui.dto.customer.CustomerDto;
import co.com.telefonica.ws.ui.dto.customer.NameGSDItem;
import co.com.telefonica.ws.ui.dto.ldap.TelcoResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AppService {
    ResponseEntity<U3ScaleResponseDto> getTokenOcp();
    ResponseEntity<U3ScaleResponseDto> getTokenProdOcp();
    ResponseEntity<Iterable<User>> getAllUsers(HttpServletRequest servletRequest);
    ResponseEntity<List<Locations>> getAllLocations();
    ResponseEntity<Optional<Locations>> findById(Long id);
    ResponseEntity<List<Locations>> getLocationByMunicipality(String municipio);
    ResponseEntity<List<Locations>> getLocationByDepartment(String department);
    ResponseEntity<List<Locations>> getLocationByRegion(String region);
    ResponseEntity<UCaptchaResponseDto> getGenerateCaptcha(HttpServletRequest servletRequest);
    ResponseEntity<UCaptchaResponseDto> getCaptchaValidate(HttpServletRequest servletRequest,
                                                           String code,
                                                           String space);
    ResponseEntity<UProductResponseDto> getProductCustomer(HttpServletRequest servletRequest,
                                                           String idType,
                                                           String idNumber);
    ResponseEntity<User> createUser(UserDto request,
                                    HttpServletRequest servletRequest);
    ResponseEntity<User> passwordUpdate(String username,
                                    String password,
                                    HttpServletRequest servletRequest);
    ResponseEntity<TelcoResponseDTO> ldapByUsernameAndPassword(String username,
                                                                  String password);
    ResponseEntity<CustomerDto> getCustomerDetail(HttpServletRequest servletRequest,
                                                  String idType,
                                                  String idNumber);
    ResponseEntity<NameGSDItem> getCompleteNameOfCustomer(HttpServletRequest request,
                                                          String idType,
                                                          String idNumber);
}
