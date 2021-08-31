package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.domain.Category;
import uz.pdp.clickup.domain.Project;
import uz.pdp.clickup.domain.Space;
import uz.pdp.clickup.domain.Status;
import uz.pdp.clickup.enums.StatusType;
import uz.pdp.clickup.mapper.MapstructMapper;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDto;
import uz.pdp.clickup.payload.StatusEditDto;
import uz.pdp.clickup.repository.CategoryRepo;
import uz.pdp.clickup.repository.ProjectRepo;
import uz.pdp.clickup.repository.SpaceRepo;
import uz.pdp.clickup.repository.StatusRepo;
import uz.pdp.clickup.service.StatusService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepo statusRepo;

    private final SpaceRepo spaceRepo;

    private final ProjectRepo projectRepo;

    private final CategoryRepo categoryRepo;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse get(UUID id) {
        Optional<Status> optionalStatus = statusRepo.findById(id);
        return optionalStatus.map(status -> new ApiResponse("OK", true, mapper.toStatusDto(status))).orElseGet(() -> new ApiResponse("Status not found", false));
    }

    @Override
    public ApiResponse getByCategory(UUID categoryId) {
        if (!categoryRepo.existsById(categoryId)){
            return new ApiResponse("Category not found", false);
        }
        return new ApiResponse("OK", true, mapper.toStatusDto(statusRepo.findAllByCategoryId(categoryId)));
    }

    @Override
    public ApiResponse create(StatusDto dto) {
        if (statusRepo.existsByNameAndSpaceId(dto.getName(), dto.getSpaceId())) {
            return new ApiResponse("Status already created!", false);
        }
        Optional<Space> optionalSpace = spaceRepo.findById(dto.getSpaceId());
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Optional<Project> optionalProject = projectRepo.findById(dto.getProjectId());
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found", false);
        }
        Optional<Category> optionalCategory = categoryRepo.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Category not found", false);
        }
        Category category = optionalCategory.get();
        Project project = optionalProject.get();
        Space space = optionalSpace.get();
        Status status = new Status(dto.getName(), dto.getColor(), space, project, category, StatusType.CUSTOM);
        statusRepo.save(status);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse edit(UUID id, StatusEditDto dto) {
        Optional<Status> optionalStatus = statusRepo.findById(id);
        if (!optionalStatus.isPresent()) {
            return new ApiResponse("Status not found", false);
        }
        Status status = optionalStatus.get();
        if (statusRepo.existsByNameAndSpaceIdAndIdNot(dto.getName(), status.getSpace().getId(), id)) {
            return new ApiResponse("Status has already existed", false);
        }
        status.setName(dto.getName());
        status.setColor(dto.getColor());
        statusRepo.save(status);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!statusRepo.existsById(id)){
            return new ApiResponse("Status not found", false);
        }
        statusRepo.deleteById(id);
        return new ApiResponse("Delete", true);
    }
}
