package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.TaskTag;

import java.util.UUID;

@Repository
public interface TaskTagRepo extends JpaRepository<TaskTag, UUID> {
    boolean existsByTagIdAndTaskId(UUID tag_id, UUID task_id);

    void deleteByTagIdAndTaskId(UUID tag_id, UUID task_id);
}
