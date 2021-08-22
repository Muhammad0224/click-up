package uz.pdp.clickup.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.domain.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
