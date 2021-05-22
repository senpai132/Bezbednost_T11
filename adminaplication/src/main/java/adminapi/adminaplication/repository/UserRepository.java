package adminapi.adminaplication.repository;


import adminapi.adminaplication.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    Page<User> findAll(Pageable pageable);
}
