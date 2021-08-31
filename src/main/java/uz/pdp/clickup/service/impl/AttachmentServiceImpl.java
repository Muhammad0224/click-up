package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import uz.pdp.clickup.domain.Attachment;
import uz.pdp.clickup.repository.AttachmentRepository;
import uz.pdp.clickup.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    private final Path root = Paths.get("C:\\opt");

    @Override
    public void download(String fileName, HttpServletResponse response) {
        Attachment attachment = attachmentRepository.findByName(fileName);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getOriginalName() + "\"");
        response.setContentType(attachment.getContentType());

        Path filePath = root.resolve(fileName).normalize();

        try {
            FileCopyUtils.copy(new FileInputStream(filePath.toString()), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
