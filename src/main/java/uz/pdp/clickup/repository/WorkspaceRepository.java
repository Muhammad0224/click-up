package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.domain.Workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    boolean existsByOwnerIdAndName(UUID owner_id, String name);

    boolean existsByOwnerIdAndNameAndIdNot(UUID owner_id, String name, Long id);

    List<Workspace> findAllByOwnerId(UUID owner_id);

    Optional<Workspace> findByIdAndOwnerId(Long id, UUID owner_id);
}
