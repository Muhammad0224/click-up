package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistDto;
import uz.pdp.clickup.payload.ChecklistItemDto;
import uz.pdp.clickup.payload.ItemUserDto;
import uz.pdp.clickup.service.ChecklistItemService;
import uz.pdp.clickup.service.ChecklistService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist/item")
@RequiredArgsConstructor
public class ChecklistItemController {
    private final ChecklistItemService checklistItemService;

    @GetMapping("/checklist/{id}")
    public ResponseEntity<?> getByChecklist(@PathVariable(value = "id") UUID checklistId) {
        ApiResponse apiResponse = checklistItemService.getByChecklist(checklistId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ChecklistItemDto dto) {
        ApiResponse apiResponse = checklistItemService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/assign/user")
    public ResponseEntity<?> assign(@Valid @RequestBody ItemUserDto dto) {
        ApiResponse apiResponse = checklistItemService.assign(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/remove/user")
    public ResponseEntity<?> removeUser(@Valid @RequestBody ItemUserDto dto) {
        ApiResponse apiResponse = checklistItemService.removeUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestParam String name) {
        ApiResponse apiResponse = checklistItemService.edit(id, name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<?> resolve(@PathVariable UUID id) {
        ApiResponse apiResponse = checklistItemService.resolve(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = checklistItemService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }



}
