package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.SpaceView;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpaceViewRepo extends JpaRepository<SpaceView, UUID> {
    List<SpaceView> findAllBySpaceId(UUID id);
}
