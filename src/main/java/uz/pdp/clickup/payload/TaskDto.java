package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
public class TaskDto {
    @NotBlank
    private String name;

    @NotNull
    private UUID statusId;

    @NotNull
    private UUID categoryId;

    private Long priorityId;

    private Timestamp startedDate;

    private boolean startTimeHas;

    private Timestamp dueDate;

    private boolean dueTimeHas;
}
