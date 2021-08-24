package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.Project;

import java.util.UUID;

@Repository
public interface ProjectRepo extends JpaRepository<Project, UUID> {
    boolean existsByNameAndSpaceId(String name, UUID space_id);
}
