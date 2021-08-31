package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.CheckList;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChecklistRepo extends JpaRepository<CheckList, UUID> {
    List<CheckList> findAllByTaskId(UUID task_id);
}
