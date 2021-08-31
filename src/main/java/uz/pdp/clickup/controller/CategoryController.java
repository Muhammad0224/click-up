package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryDto;
import uz.pdp.clickup.service.CategoryService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/category/")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        ApiResponse apiResponse = categoryService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<?> getByProject(@PathVariable(value = "id") UUID projectId) {
        ApiResponse apiResponse = categoryService.getByProject(projectId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDto dto) {
        ApiResponse apiResponse = categoryService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @Valid @RequestBody CategoryDto dto) {
        ApiResponse apiResponse = categoryService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = categoryService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
