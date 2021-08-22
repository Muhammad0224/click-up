package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.EditWorkspacePermissionDto;
import uz.pdp.clickup.payload.WorkspaceRoleDto;

public interface WorkspaceRoleService {

    ApiResponse addWorkspaceRole(WorkspaceRoleDto dto);

    ApiResponse addPermissionToRole(EditWorkspacePermissionDto dto);

    ApiResponse removePermissionToRole(EditWorkspacePermissionDto dto);
}
