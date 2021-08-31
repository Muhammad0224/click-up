package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistItemDto;
import uz.pdp.clickup.payload.ItemUserDto;

import java.util.UUID;

public interface ChecklistItemService {
    ApiResponse getByChecklist(UUID checklistId);

    ApiResponse create(ChecklistItemDto dto);

    ApiResponse edit(UUID id, String name);

    ApiResponse resolve(UUID id);

    ApiResponse delete(UUID id);

    ApiResponse assign(ItemUserDto dto);

    ApiResponse removeUser(ItemUserDto dto);
}
