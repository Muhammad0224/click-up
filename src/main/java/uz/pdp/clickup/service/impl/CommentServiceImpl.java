package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.domain.Comment;
import uz.pdp.clickup.domain.Task;
import uz.pdp.clickup.mapper.MapstructMapper;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CommentDto;
import uz.pdp.clickup.repository.CommentRepo;
import uz.pdp.clickup.repository.TaskRepo;
import uz.pdp.clickup.service.CommentService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;

    private final TaskRepo taskRepo;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse get(UUID id) {
        Optional<Comment> optionalComment = commentRepo.findById(id);
        return optionalComment.map(comment -> new ApiResponse("Ok", true, mapper.toCommentDto(comment))).orElseGet(() -> new ApiResponse("Comment not found", false));
    }

    @Override
    public ApiResponse getByTask(UUID taskId) {
        if (!taskRepo.existsById(taskId)){
            return new ApiResponse("Task not found", false);
        }
        return new ApiResponse("OK", true, mapper.toCommentDto(commentRepo.findAllByTaskId(taskId)));
    }

    @Override
    public ApiResponse addComment(CommentDto dto) {
        Optional<Task> optionalTask = taskRepo.findById(dto.getTaskId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        Comment comment = new Comment(dto.getText(), task);
        commentRepo.save(comment);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse editComment(UUID id, String text) {
        Optional<Comment> optionalComment = commentRepo.findById(id);
        if (!optionalComment.isPresent()) {
            return new ApiResponse("Comment not found", false);
        }
        Comment comment = optionalComment.get();
        comment.setText(text);
        commentRepo.save(comment);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!commentRepo.existsById(id)) {
            return new ApiResponse("Comment not found", false);
        }
        commentRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
