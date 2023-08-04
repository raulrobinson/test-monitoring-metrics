package co.com.telefonica.ws.services;

import co.com.telefonica.ws.common.utils.Constants;
import co.com.telefonica.ws.common.utils.SecurityUtils;
import co.com.telefonica.ws.common.utils.SetHeaders;
import co.com.telefonica.ws.common.utils.Utilities;
import co.com.telefonica.ws.persistence.entity.Locations;
import co.com.telefonica.ws.persistence.entity.User;
import co.com.telefonica.ws.persistence.repository.LocationsRepository;
import co.com.telefonica.ws.persistence.repository.UserRepository;
import co.com.telefonica.ws.services.client.*;
import co.com.telefonica.ws.ui.dto.*;
import co.com.telefonica.ws.ui.dto.customer.CustomerDto;
import co.com.telefonica.ws.ui.dto.customer.NameGSDItem;
import co.com.telefonica.ws.ui.dto.ldap.TelcoResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static co.com.telefonica.ws.common.utils.Utilities.latencyCalculator;

/**
 * The type App service.
 */
@Slf4j
@Service
public class AppServiceImpl implements AppService {

    @Value("${customer.detail.app.id}")
    private String appId;

    @Value("${customer.detail.app.key}")
    private String appKey;

    private static final String NOT_FOUND = "No existen registros en esta consulta";
    private static final String FOUND_REGISTERS = "Se encontraron {} registros";

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LocationsRepository locationsRepository;
    private final UAuthenticationClient uAuthenticationClient;
    private final U3ScaleClient u3ScaleClient;
    private final U3ScaleParams u3ScaleParams;
    private final UCaptchaClient uCaptchaClient;
    private final UProductClient uProductClient;
    private final U3ScaleProdClient u3ScaleProdClient;
    private final U3ScaleProdParams u3ScaleProdParams;
    private final UCustomerClient uCustomerClient;
    private final SetHeaders setHeaders;

    /**
     * Instantiates a new App service.
     *
     * @param passwordEncoder       the password encoder
     * @param userRepository        the user repository
     * @param locationsRepository   the locations repository
     * @param uAuthenticationClient the u authentication client
     * @param u3ScaleClient         the u 3Scale client
     * @param u3ScaleParams         the u 3Scale params
     * @param uCaptchaClient        the u Captcha client
     * @param uProductClient        the u Product client
     * @param u3ScaleProdClient     the u 3Scale prod client
     * @param u3ScaleProdParams     the u 3Scale prod params
     * @param uCustomerClient       the u customer client
     * @param setHeaders            the set headers
     */
    @Autowired
    public AppServiceImpl(BCryptPasswordEncoder passwordEncoder,
                          UserRepository userRepository,
                          LocationsRepository locationsRepository,
                          UAuthenticationClient uAuthenticationClient, U3ScaleClient u3ScaleClient,
                          U3ScaleParams u3ScaleParams,
                          UCaptchaClient uCaptchaClient,
                          UProductClient uProductClient, U3ScaleProdClient u3ScaleProdClient, U3ScaleProdParams u3ScaleProdParams, UCustomerClient uCustomerClient, SetHeaders setHeaders) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.locationsRepository = locationsRepository;
        this.uAuthenticationClient = uAuthenticationClient;
        this.u3ScaleClient = u3ScaleClient;
        this.u3ScaleParams = u3ScaleParams;
        this.uCaptchaClient = uCaptchaClient;
        this.uProductClient = uProductClient;
        this.u3ScaleProdClient = u3ScaleProdClient;
        this.u3ScaleProdParams = u3ScaleProdParams;
        this.uCustomerClient = uCustomerClient;
        this.setHeaders = setHeaders;
    }

    /**
     * get all users
     *
     * @param servletRequest servletRequest
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see Iterable
     */
    @Override
    public ResponseEntity<Iterable<User>> getAllUsers(HttpServletRequest servletRequest) {
        var req = userRepository.findAll();
        if (req.isEmpty()) {
            log.error(NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info(FOUND_REGISTERS, req.size());
        return new ResponseEntity<>(req, HttpStatus.OK);
    }

    /**
     * create user
     *
     * @param request request
     * @param servletRequest servletRequest
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see User
     */
    @Override
    public ResponseEntity<User> createUser(UserDto request,
                                           HttpServletRequest servletRequest) {
        var find = userRepository.findUserByUsernameAndEmail(request.getUsername(), request.getEmail());
        if (find != null) {
            log.error("User Not Found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var req = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        var res = userRepository.save(req);

        log.info("User Created with id: {}", res.getId());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * password update
     *
     * @param username username
     * @param password password
     * @param servletRequest servletRequest
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see User
     */
    @Override
    public ResponseEntity<User> passwordUpdate(String username,
                                           String password,
                                           HttpServletRequest servletRequest) {
        var find = userRepository.findUserByUsername(username);
        if (find == null) {
            log.error("User Not Found: {}", username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var req = User.builder()
                .id(find.getId())
                .username(find.getUsername())
                .email(find.getEmail())
                .role(find.getRole())
                .password(passwordEncoder.encode(password))
                .build();

        var res = userRepository.save(req);

        log.info("User with id: {} has Update Password", res.getId());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * get all locations
     *
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see List
     */
    @Override
    public ResponseEntity<List<Locations>> getAllLocations() {
        var req = locationsRepository.findAll();
        if (req.isEmpty()) {
            log.error(NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info(FOUND_REGISTERS, req.size());
        return new ResponseEntity<>(req, HttpStatus.OK);
    }

    /**
     * find by id
     *
     * @param id id
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see Optional
     */
    @Override
    public ResponseEntity<Optional<Locations>> findById(final Long id) {
        var req = this.locationsRepository.findById(id);
        if (req.isEmpty()) {
            log.error("No existe registro con id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Se encontro registro con id: {}", req.get().getId());
        return new ResponseEntity<>(req, HttpStatus.OK);
    }

    /**
     * get location by municipality
     *
     * @param municipality municipality
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see List
     */
    @Override
    public ResponseEntity<List<Locations>> getLocationByMunicipality(final String municipality) {
        var req = locationsRepository.findByMunicipioContaining(municipality);
        if (req.isEmpty()) {
            log.error(NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info(FOUND_REGISTERS, req.size());
        return new ResponseEntity<>(req, HttpStatus.OK);
    }

    /**
     * get location by department
     *
     * @param department department
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see List
     */
    @Override
    public ResponseEntity<List<Locations>> getLocationByDepartment(final String department) {
        var req = locationsRepository.findByDepartamentoContaining(StringUtils.capitalize(department));
        if (req.isEmpty()) {
            log.error(NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info(FOUND_REGISTERS, req.size());
        return new ResponseEntity<>(req, HttpStatus.OK);
    }

    /**
     * get location by region
     *
     * @param region region
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see List
     */
    @Override
    public ResponseEntity<List<Locations>> getLocationByRegion(final String region) {
        var req = locationsRepository.findByRegionContaining(StringUtils.capitalize(region));
        if (req.isEmpty()) {
            log.error(NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info(FOUND_REGISTERS, req.size());
        return new ResponseEntity<>(req, HttpStatus.OK);
    }

    /**
     * ldap by username and password
     *
     * @param username username
     * @param password password
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see TelcoResponseDTO
     */
    @Override
    public ResponseEntity<TelcoResponseDTO> ldapByUsernameAndPassword(String username,
                                                                      String password) {
        long start = System.currentTimeMillis();

        /* Headers */
        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " +
                Objects.requireNonNull(getTokenOcp().getBody()).getToken());

        /* Solicitud del Servicio Validacion de Usuario ante el LDAP-AD */
        var req = uAuthenticationClient.ldapByUsernameAndPassword(headers, username, password);

        if (req == null) {
            log.error("Error General del Servicio, Validando el Usuario en LDAP: {}", Constants.MSG_RESPONSE_FAIL);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        long end = System.currentTimeMillis();
        String latency = latencyCalculator(start, end);
        log.info("Validando el Usuario en el LDAP-AD, latencia: {}", latency);

        return new ResponseEntity<>(req, HttpStatus.OK);
    }

    /**
     * get customer detail
     *
     * @param request request
     * @param idType idType
     * @param idNumber idNumber
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see Object
     */
    @Override
    public ResponseEntity<CustomerDto> getCustomerDetail(HttpServletRequest request,
                                                         String idType,
                                                         String idNumber) {
        long start = System.currentTimeMillis();

        var headersClient = setHeaders.headerForCustomer(request);
        headersClient.add(Constants.AUTHORIZATION, Constants.BEARER +
                Objects.requireNonNull(getTokenProdOcp().getBody()).getToken());

        /* Solicitud del Servicio Customer Detail en OCP */
        var req = uCustomerClient.getCustomerDetail(
                headersClient,
                idType,
                idNumber,
                appId,
                appKey);

        if (req == null) {
            log.error("Error General del Servicio, Obteniendo el Customer Detail: {}", Constants.MSG_RESPONSE_FAIL);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        long end = System.currentTimeMillis();
        String latency = latencyCalculator(start, end);
        log.info("Se obtuvo el Customer Detail, latencia: {}", latency);

        return new ResponseEntity<>(req, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NameGSDItem> getCompleteNameOfCustomer(HttpServletRequest request,
                                                                 String idType,
                                                                 String idNumber) {
        long start = System.currentTimeMillis();

        var headersClient = setHeaders.headerForCustomer(request);
        headersClient.add(Constants.AUTHORIZATION, Constants.BEARER +
                Objects.requireNonNull(getTokenProdOcp().getBody()).getToken());

        /* Solicitud del Servicio Customer Detail en OCP */
        var req = uCustomerClient.getCustomerDetail(
                headersClient,
                idType,
                idNumber,
                appId,
                appKey);

        if (req == null) {
            log.error("Error General del Servicio, Obteniendo el Customer Detail: {}", Constants.MSG_RESPONSE_FAIL);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String firstName = String.valueOf(req.getRspBodyGCD1Item().getCustomerGSLInfoItem().getCustomerAuxInfoItem().getIndividualGSLItem().getNameGSDItem().getFirstNameCustomer());
        String middleName = String.valueOf(req.getRspBodyGCD1Item().getCustomerGSLInfoItem().getCustomerAuxInfoItem().getIndividualGSLItem().getNameGSDItem().getMiddleNameCustomer());
        String lastName = String.valueOf(req.getRspBodyGCD1Item().getCustomerGSLInfoItem().getCustomerAuxInfoItem().getIndividualGSLItem().getNameGSDItem().getLastNameCustomer());
        String secondLastName = String.valueOf(req.getRspBodyGCD1Item().getCustomerGSLInfoItem().getCustomerAuxInfoItem().getIndividualGSLItem().getNameGSDItem().getSecondLastNameCustomer());
        
        log.info(firstName + " " + middleName + " " + lastName + " " + secondLastName);

        long end = System.currentTimeMillis();
        String latency = latencyCalculator(start, end);
        log.info("Se obtuvo el Customer Detail, latencia: {}", latency);

        return new ResponseEntity<>(req
                .getRspBodyGCD1Item()
                .getCustomerGSLInfoItem()
                .getCustomerAuxInfoItem()
                .getIndividualGSLItem()
                .getNameGSDItem(), HttpStatus.OK);
    }

    /**
     * get generate captcha
     *
     * @param servletRequest servletRequest
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see UCaptchaResponseDto
     */
    @Override
    public ResponseEntity<UCaptchaResponseDto> getGenerateCaptcha(HttpServletRequest servletRequest) {
        long start = System.currentTimeMillis();

        /* Headers */
        var headers = new HttpHeaders();
        headers.add(Constants.SYSTEM, servletRequest.getHeader(Constants.SYSTEM).trim());
        headers.add(Constants.OPERATION, servletRequest.getHeader(Constants.OPERATION).trim());
        headers.add(Constants.EXECID, servletRequest.getHeader(Constants.EXECID).trim());
        headers.add(Constants.TIMESTAMP, Utilities.clientDateStringWithTZ());
        headers.add(Constants.MSGTYPE, servletRequest.getHeader(Constants.MSGTYPE).trim());
        headers.add(Constants.VARARG, servletRequest.getHeader(Constants.VARARG).trim());
        headers.add(Constants.APPID, servletRequest.getHeader(Constants.APPID).trim());
        headers.add(Constants.AUTHORIZATION, Constants.BEARER +
                Objects.requireNonNull(getTokenOcp().getBody()).getToken());

        try {
            /* Solicitud del Servicio Generate */
            var req = uCaptchaClient.generateCaptcha(headers);

            /* Validando Latencia de la Respuesta del Servicio */
            long end = System.currentTimeMillis();
            String latency = latencyCalculator(start, end);
            log.info("Se Genera un Captcha, latencia: {}", latency);

            return new ResponseEntity<>(req.getBody(), HttpStatus.OK);

        } catch (Exception ex) {
            long end = System.currentTimeMillis();
            String latency = latencyCalculator(start, end);
            log.error("Error General del Servicio, Generando el Captcha, latencia: {}", latency);

            return new ResponseEntity<>(UCaptchaResponseDto.builder()
                    .message(ex.getMessage())
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get captcha validate
     *
     * @param servletRequest servletRequest
     * @param code code
     * @param space space
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see UCaptchaResponseDto
     */
    @Override
    public ResponseEntity<UCaptchaResponseDto> getCaptchaValidate(HttpServletRequest servletRequest,
                                                                  String code,
                                                                  String space) {
        long start = System.currentTimeMillis();

        /* Headers */
        var headers = new HttpHeaders();
        headers.add(Constants.SYSTEM, servletRequest.getHeader(Constants.SYSTEM).trim());
        headers.add(Constants.OPERATION, servletRequest.getHeader(Constants.OPERATION).trim());
        headers.add(Constants.EXECID, servletRequest.getHeader(Constants.EXECID).trim());
        headers.add(Constants.TIMESTAMP, Utilities.clientDateStringWithTZ());
        headers.add(Constants.MSGTYPE, servletRequest.getHeader(Constants.MSGTYPE).trim());
        headers.add(Constants.VARARG, servletRequest.getHeader(Constants.VARARG).trim());
        headers.add(Constants.APPID, servletRequest.getHeader(Constants.APPID).trim());
        headers.add(Constants.AUTHORIZATION, Constants.BEARER +
                Objects.requireNonNull(getTokenOcp().getBody()).getToken());

        try {
            /* Solicitud del Servicio Validate */
            var req = uCaptchaClient.validateCaptcha(headers, UCaptchaValidateResponseDto.builder()
                    .code(code)
                    .space(space)
                    .build());

            /* Validando Latencia de la Respuesta del Servicio */
            long end = System.currentTimeMillis();
            String latency = latencyCalculator(start, end);
            log.info("Se Valida un Captcha, latencia: {}", latency);

            return new ResponseEntity<>(req.getBody(), HttpStatus.OK);

        } catch (Exception ex) {
            long end = System.currentTimeMillis();
            String latency = latencyCalculator(start, end);
            log.error("Error General del Servicio, Validando el Captcha, latencia: {}", latency);

            return new ResponseEntity<>(UCaptchaResponseDto.builder()
                    .message(ex.getMessage())
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get product customer
     *
     * @param servletRequest servletRequest
     * @param idType idType
     * @param idNumber idNumber
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see UProductResponseDto
     */
    @Override
    public ResponseEntity<UProductResponseDto> getProductCustomer(HttpServletRequest servletRequest,
                                                                  String idType,
                                                                  String idNumber) {
        long start = System.currentTimeMillis();

        /* Headers */
        var headers = new HttpHeaders();
        headers.add(Constants.SYSTEM, servletRequest.getHeader(Constants.SYSTEM).trim());
        headers.add(Constants.OPERATION, servletRequest.getHeader(Constants.OPERATION).trim());
        headers.add(Constants.EXECID, servletRequest.getHeader(Constants.EXECID).trim());
        headers.add(Constants.TIMESTAMP, Utilities.clientDateStringWithTZ());
        headers.add(Constants.MSGTYPE, servletRequest.getHeader(Constants.MSGTYPE).trim());
        headers.add(Constants.VARARG, servletRequest.getHeader(Constants.VARARG).trim());
        headers.add(Constants.APPID, servletRequest.getHeader(Constants.APPID).trim());
        headers.add(Constants.AUTHORIZATION, Constants.BEARER +
                Objects.requireNonNull(getTokenOcp().getBody()).getToken());

        var req = uProductClient.getProductList(headers, idType, idNumber);
        List<UProductItemDto> itemDtos = new ArrayList<>();

        for (int i = 0; i < Objects.requireNonNull(req.getBody()).getServiceResponse().size(); i++) {
            var item = UProductItemDto.builder().build();
            item.setNmAcc(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getNmAcc()));
            item.setClientId(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getClientId()));
            item.setProduct(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getProduct()));
            item.setProductType(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getProductType()));
            item.setServiceNumber(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getServiceNumber()));
            item.setPaymentReference(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getPaymentReference()));
            item.setStatus(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getStatus()));
            item.setStCont(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getStCont()));
            item.setCdPlan(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getCdPlan()));
            item.setNamePlan(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getNamePlan()));
            item.setIdentificationType(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getIdentificationType()));
            item.setIdentificationNumber(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getIdentificationNumber()));
            item.setDigitalInvoice(SecurityUtils.blindStr(Objects.requireNonNull(req.getBody()).getServiceResponse().get(i).getDigitalInvoice()));
            itemDtos.add(item);
        }

        /* Validando Latencia de la Respuesta del Servicio */
        long end = System.currentTimeMillis();
        String latency = latencyCalculator(start, end);
        log.info("Listado de Productos, latencia: {}", latency);
        return new ResponseEntity<>(UProductResponseDto.builder()
                .product(itemDtos)
                .build(), HttpStatus.OK);
    }

    /**
     * get token desarrollo ocp
     *
     * @return {@link U3ScaleResponseDto}
     * @see U3ScaleResponseDto
     */
    @Override
    public ResponseEntity<U3ScaleResponseDto> getTokenOcp() {

        long start = System.currentTimeMillis();

        try {
            /* Solicitud del Servicio Obtener Token OCP */
            U3ScaleOcpResponse req = u3ScaleClient.getTokenOcp(
                    this.u3ScaleParams.getChannel(),
                    this.u3ScaleParams.getGrantType(),
                    this.u3ScaleParams.getClientId(),
                    this.u3ScaleParams.getClientSecret(),
                    this.u3ScaleParams.getScope());

            /* Validando Latencia de la Respuesta del Servicio */
            long end = System.currentTimeMillis();
            String latency = latencyCalculator(start, end);
            log.info("Se Genera un Token para Desarrollo de OCP, latencia: {}", latency);

            return new ResponseEntity<>(U3ScaleResponseDto.builder()
                    .token(req.getAccessToken().trim())
                    .refreshToken(req.getRefreshToken().trim())
                    .expiresIn(req.getExpiresIn().trim())
                    .refreshExpiresIn(req.getRefreshExpiresIn().trim())
                    .build(), HttpStatus.OK);

        } catch (Exception ex) {
            long end = System.currentTimeMillis();
            String latency = latencyCalculator(start, end);
            log.error("Error General del Servicio, Generando el Token para Desarrollo de OCP, latencia: {}", latency);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get token prod ocp
     *
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see U3ScaleResponseDto
     */
    @Override
    public ResponseEntity<U3ScaleResponseDto> getTokenProdOcp() {

        long start = System.currentTimeMillis();

        try {
            /* Solicitud del Servicio Obtener Token OCP */
            U3ScaleOcpResponse req = u3ScaleProdClient.getTokenProdOcp(
                    this.u3ScaleProdParams.getChannel(),
                    this.u3ScaleProdParams.getGrantType(),
                    this.u3ScaleProdParams.getClientId(),
                    this.u3ScaleProdParams.getClientSecret(),
                    this.u3ScaleProdParams.getScope());

            /* Validando Latencia de la Respuesta del Servicio */
            long end = System.currentTimeMillis();
            String latency = latencyCalculator(start, end);
            log.info("Se Genera un Token para Produccion de OCP, latencia: {}", latency);

            return new ResponseEntity<>(U3ScaleResponseDto.builder()
                    .token(req.getAccessToken().trim())
                    .refreshToken(req.getRefreshToken().trim())
                    .expiresIn(req.getExpiresIn().trim())
                    .refreshExpiresIn(req.getRefreshExpiresIn().trim())
                    .build(), HttpStatus.OK);

        } catch (Exception ex) {
            long end = System.currentTimeMillis();
            String latency = latencyCalculator(start, end);
            log.error("Error General del Servicio, Generando el Token para Produccion de OCP, latencia: {}", latency);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
