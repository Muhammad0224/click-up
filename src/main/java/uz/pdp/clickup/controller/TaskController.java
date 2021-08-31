package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickup.payload.*;
import uz.pdp.clickup.service.TaskService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        ApiResponse apiResponse = taskService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getByCategory(@PathVariable(value = "id") UUID categoryId) {
        ApiResponse apiResponse = taskService.getByCategory(categoryId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TaskDto dto) {
        ApiResponse apiResponse = taskService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/subtask")
    public ResponseEntity<?> createSubtask(@Valid @RequestBody SubtaskDto dto) {
        ApiResponse apiResponse = taskService.createSubtask(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/change/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam UUID statusId) {
        ApiResponse apiResponse = taskService.changeStatus(id, statusId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/change/priority/{id}")
    public ResponseEntity<?> changePriority(@PathVariable UUID id, @RequestParam Long priorityId) {
        ApiResponse apiResponse = taskService.changePriority(id, priorityId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/change/estimate/{id}")
    public ResponseEntity<?> changeEstimate(@PathVariable UUID id, @RequestParam Long estimate) {
        ApiResponse apiResponse = taskService.changeEstimate(id, estimate);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/change/description/{id}")
    public ResponseEntity<?> changeDescription(@PathVariable UUID id, @RequestParam String description) {
        ApiResponse apiResponse = taskService.changeDescription(id, description);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/attach/file/{id}")
    public ResponseEntity<?> attachFile(@PathVariable UUID id, @RequestParam MultipartFile file) {
        ApiResponse apiResponse = taskService.attachFile(id, file);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/detach/file/{id}")
    public ResponseEntity<?> detachFile(@PathVariable UUID id, @RequestParam String fileName) {
        ApiResponse apiResponse = taskService.detachFile(id, fileName);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/due/date/{id}")
    public ResponseEntity<?> dueDate(@PathVariable UUID id, @RequestBody DueDateDto dto) {
        ApiResponse apiResponse = taskService.dueDate(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/add/tag")
    public ResponseEntity<?> addTag(@Valid @RequestBody TaskTagDto dto) {
        ApiResponse apiResponse = taskService.addTag(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/remove/tag")
    public ResponseEntity<?> removeTag(@RequestParam UUID tagId, @RequestParam UUID taskId) {
        ApiResponse apiResponse = taskService.removeTag(tagId, taskId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/assign/user")
    public ResponseEntity<?> assignUser(@RequestParam UUID taskId, @RequestParam UUID userId) {
        ApiResponse apiResponse = taskService.assignUser(taskId, userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/remove/user")
    public ResponseEntity<?> removeUser(@RequestParam UUID taskId, @RequestParam UUID userId) {
        ApiResponse apiResponse = taskService.removeUser(taskId, userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
