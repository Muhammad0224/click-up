package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.clickup.enums.AddType;

import java.sql.Timestamp;

@Setter
@Getter
public class DueDateDto {
    private Timestamp startedDate;

    private boolean startTimeHas = false;

    private boolean dueTimeHas = false;

    private Timestamp dueDate;

    private AddType addType;
}
