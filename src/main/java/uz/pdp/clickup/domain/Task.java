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

    private boolean startTimeHas;

    private boolean dueTimeHas;

    @Column
    private Timestamp dueDate;

    @Column
    private Long estimateTime;

    @Column
    private Timestamp activateDate;
}
