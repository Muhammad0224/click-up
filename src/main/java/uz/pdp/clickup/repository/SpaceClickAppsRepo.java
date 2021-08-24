package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.SpaceClickApps;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpaceClickAppsRepo extends JpaRepository<SpaceClickApps, UUID> {
    List<SpaceClickApps> findAllBySpaceId(UUID id);
}
