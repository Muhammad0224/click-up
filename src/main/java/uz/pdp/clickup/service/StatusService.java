package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDto;
import uz.pdp.clickup.payload.StatusEditDto;

import java.util.UUID;

public interface StatusService {
    ApiResponse get(UUID id);

    ApiResponse getByCategory(UUID categoryId);

    ApiResponse create(StatusDto dto);

    ApiResponse edit(UUID id, StatusEditDto dto);

    ApiResponse delete(UUID id);
}
