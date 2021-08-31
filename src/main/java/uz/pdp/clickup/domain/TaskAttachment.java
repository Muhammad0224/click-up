package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskAttachment extends AbsUUIDEntity {
    @ManyToOne
    private Task task;

    @ManyToOne
    private Attachment attachment;

    @Column
    private boolean pinCoverImage = false;

    public TaskAttachment(Task task, Attachment attachment) {
        this.task = task;
        this.attachment = attachment;
    }
}
