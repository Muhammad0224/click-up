package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.Status;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatusRepo extends JpaRepository<Status, UUID> {
    boolean existsByNameAndSpaceId(String name, UUID space_id);

    boolean existsByNameAndSpaceIdAndIdNot(String name, UUID space_id, UUID id);

    List<Status> findAllByCategoryId(UUID category_id);
}
