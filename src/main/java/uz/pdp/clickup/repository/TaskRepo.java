package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
    List<Task> findAllByCategoryId(UUID category_id);

}
