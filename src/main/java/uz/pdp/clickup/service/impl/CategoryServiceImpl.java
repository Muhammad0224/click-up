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
import uz.pdp.clickup.payload.CategoryDto;
import uz.pdp.clickup.repository.CategoryRepo;
import uz.pdp.clickup.repository.ProjectRepo;
import uz.pdp.clickup.repository.SpaceRepo;
import uz.pdp.clickup.repository.StatusRepo;
import uz.pdp.clickup.service.CategoryService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    private final ProjectRepo projectRepo;

    private final StatusRepo statusRepo;

    private final SpaceRepo spaceRepo;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse get(UUID id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        return optionalCategory.map(category -> new ApiResponse("OK", true, mapper.toCategoryDto(category))).orElseGet(() -> new ApiResponse("Category not found", false));
    }

    @Override
    public ApiResponse getByProject(UUID projectId) {
        if (!projectRepo.existsById(projectId)) {
            return new ApiResponse("Project not found", false);
        }
        return new ApiResponse("OK", true, mapper.toCategoryDto(categoryRepo.findAllByProjectId(projectId)));
    }

    @Override
    public ApiResponse create(CategoryDto dto) {
        if (categoryRepo.existsByNameAndProjectId(dto.getName(), dto.getProjectId())) {
            return new ApiResponse("Category has already existed", false);
        }
        Optional<Project> optionalProject = projectRepo.findById(dto.getProjectId());
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found", false);
        }
        Optional<Space> optionalSpace = spaceRepo.findById(dto.getSpaceId());
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        Project project = optionalProject.get();
        Category category = new Category(dto.getName(), project);
        statusRepo.save(new Status(
                "TO DO",
                "gray",
                space,
                project,
                category,
                StatusType.OPEN
        ));
        statusRepo.save(new Status(
                "Completed",
                "green",
                space,
                project,
                category,
                StatusType.CLOSED
        ));
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse edit(UUID id, CategoryDto dto) {
        if (categoryRepo.existsByNameAndProjectIdAndIdNot(dto.getName(), dto.getProjectId(), id)) {
            return new ApiResponse("Category has already existed", false);
        }
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Category not found", false);
        }
        Optional<Project> optionalProject = projectRepo.findById(dto.getProjectId());
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found", false);
        }
        Project project = optionalProject.get();
        Category category = optionalCategory.get();
        category.setName(dto.getName());
        category.setProject(project);
        categoryRepo.save(category);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!categoryRepo.existsById(id)) {
            return new ApiResponse("Category not found", false);
        }
        categoryRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
