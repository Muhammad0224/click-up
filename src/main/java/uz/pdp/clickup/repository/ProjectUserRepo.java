package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.ProjectUser;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectUserRepo extends JpaRepository<ProjectUser, UUID> {
    boolean existsByProjectIdAndMemberId(UUID project_id, UUID member_id);

    void deleteAllByProjectIdAndMemberId(UUID project_id, UUID member_id);

}
