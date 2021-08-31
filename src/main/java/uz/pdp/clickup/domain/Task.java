package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task extends AbsUUIDEntity {
    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    private Status status;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Priority priority;

    @ManyToOne
    private Task parent;

    @Column
    private Timestamp startedDate;

    private boolean startTimeHas = false;

    private boolean dueTimeHas = false;

    @Column
    private Timestamp dueDate;

    @Column
    private Long estimateTime;

    @Column
    private Timestamp activateDate;

    public Task(String name, Status status, Category category, Priority priority, Timestamp startedDate, boolean startTimeHas, boolean dueTimeHas, Timestamp dueDate) {
        this.name = name;
        this.status = status;
        this.category = category;
        this.priority = priority;
        this.startedDate = startedDate;
        this.startTimeHas = startTimeHas;
        this.dueTimeHas = dueTimeHas;
        this.dueDate = dueDate;
    }

    public Task(String name, Priority priority, Task parent, Timestamp startedDate, boolean startTimeHas, boolean dueTimeHas, Timestamp dueDate, Long estimateTime, Timestamp activateDate) {
        this.name = name;
        this.priority = priority;
        this.parent = parent;
        this.startedDate = startedDate;
        this.startTimeHas = startTimeHas;
        this.dueTimeHas = dueTimeHas;
        this.dueDate = dueDate;
        this.estimateTime = estimateTime;
        this.activateDate = activateDate;
    }
}
