package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.domain.Tag;
import uz.pdp.clickup.mapper.MapstructMapper;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TagEditDto;
import uz.pdp.clickup.repository.TagRepo;
import uz.pdp.clickup.repository.WorkspaceRepository;
import uz.pdp.clickup.service.TagService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepo tagRepo;

    private final WorkspaceRepository workspaceRepository;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse get(Long workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            return new ApiResponse("Workspace not found", false);
        }
        return new ApiResponse("OK", true, mapper.toTagDto(tagRepo.findAllByWorkspaceId(workspaceId)));
    }

    @Override
    public ApiResponse editTag(UUID tagId, TagEditDto dto) {
        Optional<Tag> optionalTag = tagRepo.findById(tagId);
        if (!optionalTag.isPresent()) {
            return new ApiResponse("Tag not found", false);
        }
        Tag tag = optionalTag.get();
        tag.setName(dto.getName());
        tag.setColor(dto.getColor());
        tagRepo.save(tag);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse deleteTag(UUID tagId) {
        if (!tagRepo.existsById(tagId)) {
            return new ApiResponse("Tag not found", false);
        }
        tagRepo.deleteById(tagId);
        return new ApiResponse("Deleted", true);
    }
}
