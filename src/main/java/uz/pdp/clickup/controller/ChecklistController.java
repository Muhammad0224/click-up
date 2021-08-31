package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistDto;
import uz.pdp.clickup.service.ChecklistService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checklistService;

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getChecklistByTask(@PathVariable(value = "id") UUID taskId) {
        ApiResponse apiResponse = checklistService.getChecklistByTask(taskId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ChecklistDto dto) {
        ApiResponse apiResponse = checklistService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestParam String name) {
        ApiResponse apiResponse = checklistService.edit(id, name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = checklistService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
