package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.Category;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {
    boolean existsByNameAndProjectId(String name, UUID project_id);

    boolean existsByNameAndProjectIdAndIdNot(String name, UUID project_id, UUID id);

    List<Category> findAllByProjectId(UUID project_id);
}
