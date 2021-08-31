package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistDto;

import java.util.UUID;

public interface ChecklistService {
    ApiResponse getChecklistByTask(UUID taskId);

    ApiResponse create(ChecklistDto dto);

    ApiResponse edit(UUID id, String name);

    ApiResponse delete(UUID id);
}
