package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.SpaceUser;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpaceUserRepo extends JpaRepository<SpaceUser, UUID> {
    boolean existsByMemberIdAndSpaceId(UUID member_id, UUID space_id);

    List<SpaceUser> findAllBySpaceId(UUID space_id);
}
