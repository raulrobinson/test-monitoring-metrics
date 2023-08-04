package co.com.telefonica.ws.persistence.repository;

import co.com.telefonica.ws.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsernameAndEmail(String username, String email);
    User findUserByUsername(String username);
}
