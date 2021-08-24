package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.View;

@Repository
public interface ViewRepo extends JpaRepository<View, Long> {
    View getByName(String name);
}
