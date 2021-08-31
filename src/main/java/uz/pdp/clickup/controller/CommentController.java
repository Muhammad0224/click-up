package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CommentDto;
import uz.pdp.clickup.service.CommentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        ApiResponse apiResponse = commentService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getByTask(@PathVariable(value = "id") UUID taskId) {
        ApiResponse apiResponse = commentService.getByTask(taskId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDto dto) {
        ApiResponse apiResponse = commentService.addComment(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editComment(@PathVariable UUID id, @RequestParam String text) {
        ApiResponse apiResponse = commentService.editComment(id, text);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = commentService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
