package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDTO;
import uz.pdp.clickup.payload.WorkspaceDTO;
import uz.pdp.clickup.security.CurrentUser;
import uz.pdp.clickup.service.WorkspaceService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @GetMapping("/{id}/members")
    public HttpEntity<?> getWorkspaceMembers(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.getWorkspaceMembers(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}/guests")
    public HttpEntity<?> getWorkspaceGuests(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.getWorkspaceGuests(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/user/{id}")
    public HttpEntity<?> getUserWorkspaces(@PathVariable(value = "id") UUID userId) {
        ApiResponse apiResponse = workspaceService.getUserWorkspaces(userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public HttpEntity<?> addWorkspace(@Valid @RequestBody WorkspaceDTO dto, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addWorkspace(dto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * NAME, COLOR, AVATAR O'ZGARAISHI MUMKIN
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?> editWorkspace(@PathVariable Long id, @RequestBody WorkspaceDTO dto) {
        ApiResponse apiResponse = workspaceService.editWorkspace(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * @param id
     * @param ownerId
     * @return
     */
    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwnerWorkspace(@PathVariable Long id,
                                              @RequestParam UUID ownerId) {
        ApiResponse apiResponse = workspaceService.changeOwnerWorkspace(id, ownerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * ISHXONANI O'CHIRISH
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkspace(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?> addOrEditOrRemoveWorkspace(@PathVariable Long id,
                                                    @RequestBody MemberDTO dto) {
        ApiResponse apiResponse = workspaceService.addOrEditOrRemoveWorkspace(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/join")
    public HttpEntity<?> joinToWorkspace(@RequestParam Long id,
                                         @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
