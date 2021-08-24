package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.Icon;

@Repository
public interface IconRepo extends JpaRepository<Icon, Long> {
}
