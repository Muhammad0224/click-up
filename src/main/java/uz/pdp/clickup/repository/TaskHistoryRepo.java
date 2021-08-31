package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.TaskHistory;

import java.util.UUID;

@Repository
public interface TaskHistoryRepo extends JpaRepository<TaskHistory, UUID> {
}
