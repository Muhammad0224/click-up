package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CommentDto;

import java.util.UUID;

public interface CommentService {
    ApiResponse get(UUID id);

    ApiResponse getByTask(UUID taskId);

    ApiResponse addComment(CommentDto dto);

    ApiResponse editComment(UUID id, String text);

    ApiResponse delete(UUID id);
}
