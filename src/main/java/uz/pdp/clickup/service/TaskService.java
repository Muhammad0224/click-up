package uz.pdp.clickup.service;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickup.payload.*;

import java.util.UUID;

public interface TaskService {
    ApiResponse get(UUID id);

    ApiResponse getByCategory(UUID categoryId);

    ApiResponse create(TaskDto dto);

    ApiResponse changeStatus(UUID id, UUID statusId);

    ApiResponse changeDescription(UUID id, String description);

    ApiResponse attachFile(UUID id, MultipartFile file);

    ApiResponse detachFile(UUID id, String fileName);

    ApiResponse createSubtask(SubtaskDto dto);

    ApiResponse dueDate(UUID id, DueDateDto dto);

    ApiResponse changePriority(UUID id, Long priorityId);

    ApiResponse changeEstimate(UUID id, Long estimate);

    ApiResponse addTag(TaskTagDto dto);

    ApiResponse removeTag(UUID tagId, UUID taskId);

    ApiResponse assignUser(UUID taskId, UUID userId);

    ApiResponse removeUser(UUID taskId, UUID userId);
}
