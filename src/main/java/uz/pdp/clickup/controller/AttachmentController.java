package uz.pdp.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping("/download/{name}")
    public ResponseEntity<?> download(@PathVariable(value = "name") String fileName, HttpServletResponse response){
        attachmentService.download(fileName, response);
        return ResponseEntity.ok().body(new ApiResponse("Ok", true));
    }
}
