package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.TaskAttachment;

import java.util.UUID;

@Repository
public interface TaskAttachmentRepo extends JpaRepository<TaskAttachment, UUID> {
    void deleteByAttachmentIdAndTaskId(UUID attachment_id, UUID task_id);

}
