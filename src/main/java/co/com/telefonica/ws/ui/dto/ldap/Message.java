package co.com.telefonica.ws.ui.dto.ldap;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    private List<String> groups;
    private User user;
}
