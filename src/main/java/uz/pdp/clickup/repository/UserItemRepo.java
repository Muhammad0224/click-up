package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.domain.ChecklistItemUser;

import java.util.UUID;

@Repository
public interface UserItemRepo extends JpaRepository<ChecklistItemUser, UUID> {
    boolean existsByUserIdAndCheckListItemId(UUID user_id, UUID checkListItem_id);

    void deleteByUserIdAndCheckListItemId(UUID user_id, UUID checkListItem_id);
}
