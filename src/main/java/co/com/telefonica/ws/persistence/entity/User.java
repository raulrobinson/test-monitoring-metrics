package co.com.telefonica.ws.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tbl_users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 3317686311392412458L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String role;
    private String email;

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", role=" + role + "]";
    }
}
