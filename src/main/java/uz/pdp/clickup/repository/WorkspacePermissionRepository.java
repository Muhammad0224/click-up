package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.clickup.domain.WorkspacePermission;

import java.util.List;
import java.util.UUID;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, UUID> {
    @Query("select w from WorkspacePermission w where w.workspaceRole.workspace.id = ?1 and w.workspaceRole.name = ?2")
    List<WorkspacePermission> findAllByRolePermission(Long workspaceRole_workspace_id, String workspaceRole_name);

}
