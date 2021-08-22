package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.domain.Workspace;
import uz.pdp.clickup.domain.WorkspacePermission;
import uz.pdp.clickup.domain.WorkspaceRole;
import uz.pdp.clickup.enums.WorkspacePermissionName;
import uz.pdp.clickup.enums.WorkspaceRoleName;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.EditWorkspacePermissionDto;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.repository.WorkspacePermissionRepository;
import uz.pdp.clickup.repository.WorkspaceRepository;
import uz.pdp.clickup.repository.WorkspaceRoleRepository;
import uz.pdp.clickup.security.CurrentUser;
import uz.pdp.clickup.service.WorkspaceRoleService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceRoleServiceImpl implements WorkspaceRoleService {
    private final WorkspaceRoleRepository workspaceRoleRepository;

    private final WorkspaceRepository workspaceRepository;

    private final WorkspacePermissionRepository workspacePermissionRepository;


    @Override
    public ApiResponse addWorkspaceRole(WorkspaceRoleDto dto) {
        if (workspaceRoleRepository.existsByNameAndWorkspaceId(dto.getName(), dto.getWorkspaceId())) {
            return new ApiResponse("Role has already created", false);
        }
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(dto.getExtendsRoleId());
        if (!optionalWorkspaceRole.isPresent()) {
            return new ApiResponse("Workspace role not found", false);
        }
        WorkspaceRole extendsRole = optionalWorkspaceRole.get();
        WorkspaceRole workspaceRole = new WorkspaceRole(
                workspace,
                dto.getName(),
                WorkspaceRoleName.valueOf(extendsRole.getName())
        );
        WorkspaceRole savedWorkspaceRole = workspaceRoleRepository.save(workspaceRole);

        List<WorkspacePermission> permissions = new ArrayList<>();
        for (WorkspacePermission permission : workspacePermissionRepository.findAllByRolePermission(dto.getWorkspaceId(), extendsRole.getName())) {
            WorkspacePermission workspacePermission = new WorkspacePermission(savedWorkspaceRole, permission.getPermission());
            permissions.add(workspacePermission);
        }
        workspacePermissionRepository.saveAll(permissions);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse addPermissionToRole(EditWorkspacePermissionDto dto) {
        if (!workspaceRepository.existsById(dto.getWorkspaceId())) {
            return new ApiResponse("Workspace not found", false);
        }
        Optional<WorkspaceRole> optionalRole = workspaceRoleRepository.findByIdAndWorkspaceId(dto.getRoleId(), dto.getWorkspaceId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found", false);
        }
        WorkspaceRole workspaceRole = optionalRole.get();
        List<WorkspacePermission> permissions = workspacePermissionRepository.findAllByRolePermission(dto.getWorkspaceId(), workspaceRole.getName());
        List<WorkspacePermission> newPermission = new ArrayList<>(permissions);
        for (String dtoPermission : dto.getPermissions()) {
            boolean has = false;
            for (WorkspacePermission permission : permissions) {
                if (permission.getPermission().equals(WorkspacePermissionName.valueOf(dtoPermission))) {
                    has = true;
                    break;
                }
            }
            if (!has){
                newPermission.add(new WorkspacePermission(
                        workspaceRole,
                        WorkspacePermissionName.valueOf(dtoPermission)
                ));
            }
        }
        workspacePermissionRepository.saveAll(newPermission);
        return new ApiResponse("Edited", true);
    }

    @Override
    public ApiResponse removePermissionToRole(EditWorkspacePermissionDto dto) {
        if (!workspaceRepository.existsById(dto.getWorkspaceId())) {
            return new ApiResponse("Workspace not found", false);
        }
        Optional<WorkspaceRole> optionalRole = workspaceRoleRepository.findByIdAndWorkspaceId(dto.getRoleId(), dto.getWorkspaceId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found", false);
        }
        WorkspaceRole workspaceRole = optionalRole.get();
        List<WorkspacePermission> permissions = workspacePermissionRepository.findAllByRolePermission(dto.getWorkspaceId(), workspaceRole.getName());
        List<WorkspacePermission> deletedPermissions = new ArrayList<>();
        for (String dtoPermission : dto.getPermissions()) {
            for (WorkspacePermission permission : permissions) {
                if (permission.getPermission().equals(WorkspacePermissionName.valueOf(dtoPermission))) {
                    deletedPermissions.add(permission);
                }
            }
        }
        workspacePermissionRepository.deleteAll(deletedPermissions);
        return new ApiResponse("Edited", true);
    }
}
