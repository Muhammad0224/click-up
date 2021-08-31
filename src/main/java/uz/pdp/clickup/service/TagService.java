package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TagEditDto;

import java.util.UUID;

public interface TagService {
    ApiResponse get(Long workspaceId);

    ApiResponse editTag(UUID tagId, TagEditDto dto);

    ApiResponse deleteTag(UUID tagId);
}
