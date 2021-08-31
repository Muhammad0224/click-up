package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.Tag;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepo extends JpaRepository<Tag, UUID> {
    List<Tag> findAllByWorkspaceId(Long workspace_id);
}
