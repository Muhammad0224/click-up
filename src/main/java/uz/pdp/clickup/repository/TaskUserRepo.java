package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.TaskUser;

import java.util.UUID;

@Repository
public interface TaskUserRepo extends JpaRepository<TaskUser, UUID> {
    boolean existsByUserIdAndTaskId(UUID user_id, UUID task_id);

    void deleteByUserIdAndTaskId(UUID user_id, UUID task_id);
}
