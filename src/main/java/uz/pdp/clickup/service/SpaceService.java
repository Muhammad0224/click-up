package uz.pdp.clickup.service;

import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.payload.*;

import java.util.UUID;

public interface SpaceService {
    ApiResponse get(UUID id);

    ApiResponse get(Long workspaceId);

    ApiResponse addSpace(SpaceDto dto, User user);

    ApiResponse editSpace(SpaceDto dto, UUID id);

    ApiResponse attachMembers(AttachMemberDto dto, UUID id);

    ApiResponse attachViews(AttachViewsDto dto, UUID id);

    ApiResponse attachClickApp(AttachClickAppDto dto, UUID id);

    ApiResponse detachMembers(AttachMemberDto dto, UUID id);

    ApiResponse detachViews(AttachViewsDto dto, UUID id);

    ApiResponse detachClickApp(AttachClickAppDto dto, UUID id);

    ApiResponse delete(UUID id);
}
