package co.com.telefonica.ws.ui.dto.ldap;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String identifierNumber;
    private String name;
    private String company;
    private List<String> memberOf;
    private String department;
    private String email;
}
