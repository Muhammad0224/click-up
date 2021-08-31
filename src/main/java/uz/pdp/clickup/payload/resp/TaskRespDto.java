package uz.pdp.clickup.payload.resp;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.clickup.domain.Category;
import uz.pdp.clickup.domain.Priority;
import uz.pdp.clickup.domain.Status;
import uz.pdp.clickup.domain.Task;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
public class TaskRespDto {
    private String name;

    private String description;

    private Status status;

    private UUID categoryId;

    private Long priorityId;

    private UUID parentId;

    private Timestamp startedDate;

    private boolean startTimeHas = false;

    private boolean dueTimeHas = false;

    private Timestamp dueDate;

    private Long estimateTime;

    private Timestamp activateDate;

}
