package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.Space;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpaceRepo extends JpaRepository<Space, UUID> {
    boolean existsByNameAndWorkspaceId(String name, Long workspace_id);

    boolean existsByNameAndWorkspaceIdAndIdNot(String name, Long workspace_id, UUID id);

    List<Space> findAllByWorkspaceId(Long workspace_id);
}
