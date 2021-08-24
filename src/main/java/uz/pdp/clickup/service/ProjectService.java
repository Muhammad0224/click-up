package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.AttachMemberPermissionDto;
import uz.pdp.clickup.payload.ProjectDto;

import java.util.UUID;

public interface ProjectService {
    ApiResponse create(ProjectDto dto);

    ApiResponse attachMember(UUID id, AttachMemberPermissionDto dto);

    ApiResponse deleteMember(UUID id, UUID memberId);
}
