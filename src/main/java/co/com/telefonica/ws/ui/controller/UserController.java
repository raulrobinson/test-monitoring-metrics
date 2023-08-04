package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.common.utils.Constants;
import co.com.telefonica.ws.common.utils.SecurityUtils;
import co.com.telefonica.ws.common.utils.Utilities;
import co.com.telefonica.ws.common.utils.ValidateHeaders;
import co.com.telefonica.ws.services.AppService;
import co.com.telefonica.ws.ui.dto.ResponseDto;
import co.com.telefonica.ws.ui.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

/**
 * The type Authentication controller.
 */
@RestController
@RequestMapping(path = "${controller.properties.base-path}" + "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "API de Users. Contiene las operaciones sobre los usuarios del sistema.")
public class UserController {

    private final AppService appService;

    /**
     * Instantiates a new User controller.
     *
     * @param appService         the app service
     */
    public UserController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("all-users")
    public ResponseEntity<ResponseDto> getAllUsers(final @RequestHeader HttpHeaders headers,
                                                   HttpServletRequest servletRequest) {

        headers.add(Constants.TIMESTAMP, ValidateHeaders.getTimestampValue());

        var req = appService.getAllUsers(servletRequest);
        var res = SecurityUtils.blindUsersList(Objects.requireNonNull(req.getBody()));

        if (req.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(SecurityUtils.blindStr(Utilities.getTimestampValue()))
                    .message(SecurityUtils.blindStr(String.valueOf(req.getStatusCode())))
                    .serviceResponse(res)
                    .build(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("create-user")
    public ResponseEntity<ResponseDto> createUser(final @RequestBody UserDto request,
                                                  final @RequestHeader HttpHeaders headers,
                                                  HttpServletRequest servletRequest) {

        headers.add(Constants.TIMESTAMP, ValidateHeaders.getTimestampValue());

        var req = appService.createUser(request, servletRequest);
        var res = SecurityUtils.blindUsers(Optional.ofNullable(req.getBody()));

        if (req.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(SecurityUtils.blindStr(Utilities.getTimestampValue()))
                    .message(SecurityUtils.blindStr(String.valueOf(req.getStatusCode())))
                    .serviceResponse(res)
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("password-update/{username}")
    public ResponseEntity<ResponseDto> passwordUpdate(final @RequestParam(name = "username") String username,
                                                  final @RequestParam(name = "password") String password,
                                                  final @RequestHeader HttpHeaders headers,
                                                  HttpServletRequest servletRequest) {

        headers.add(Constants.TIMESTAMP, ValidateHeaders.getTimestampValue());

        var req = appService.passwordUpdate(username, password, servletRequest);
        var res = SecurityUtils.blindUsers(Optional.ofNullable(req.getBody()));

        if (req.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(SecurityUtils.blindStr(Utilities.getTimestampValue()))
                    .message(SecurityUtils.blindStr(String.valueOf(req.getStatusCode())))
                    .serviceResponse(res)
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

}
