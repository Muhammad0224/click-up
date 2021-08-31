package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDto;
import uz.pdp.clickup.payload.StatusEditDto;
import uz.pdp.clickup.service.StatusService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        ApiResponse apiResponse = statusService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getByCategory(@PathVariable(value = "id") UUID categoryId) {
        ApiResponse apiResponse = statusService.getByCategory(categoryId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody StatusDto dto) {
        ApiResponse apiResponse = statusService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @Valid @RequestBody StatusEditDto dto) {
        ApiResponse apiResponse = statusService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = statusService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
