package uz.pdp.clickup.service;

import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDTO;
import uz.pdp.clickup.payload.WorkspaceDTO;

import java.util.UUID;

public interface WorkspaceService {

    ApiResponse addWorkspace(WorkspaceDTO dto, User user);

    ApiResponse editWorkspace(Long id, WorkspaceDTO dto);

    ApiResponse changeOwnerWorkspace(Long id, UUID ownerId);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO dto);

    ApiResponse joinToWorkspace(Long id, User user);

    ApiResponse getWorkspaceMembers(Long id);

    ApiResponse getWorkspaceGuests(Long id);

    ApiResponse getUserWorkspaces(UUID userId);
}
