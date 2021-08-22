package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.EditWorkspacePermissionDto;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.service.WorkspaceRoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/workspace/role")
@RequiredArgsConstructor
public class WorkspaceRoleController {
    private final WorkspaceRoleService workspaceRoleService;

    @PostMapping
    public HttpEntity<?> addWorkspaceRole(@Valid @RequestBody WorkspaceRoleDto dto) {
        ApiResponse apiResponse = workspaceRoleService.addWorkspaceRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/add/permission")
    public HttpEntity<?> addPermissionToRole(@RequestBody EditWorkspacePermissionDto dto) {
        ApiResponse apiResponse = workspaceRoleService.addPermissionToRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/remove/permission")
    public HttpEntity<?> removePermissionToRole(@RequestBody EditWorkspacePermissionDto dto) {
        ApiResponse apiResponse = workspaceRoleService.removePermissionToRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
