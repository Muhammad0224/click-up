
package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryDto;

import java.util.UUID;

public interface CategoryService {
    ApiResponse get(UUID id);

    ApiResponse getByProject(UUID projectId);

    ApiResponse create(CategoryDto dto);

    ApiResponse edit(UUID id, CategoryDto dto);

    ApiResponse delete(UUID id);
}
