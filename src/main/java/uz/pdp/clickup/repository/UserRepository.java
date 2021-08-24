package uz.pdp.clickup.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
