package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickup.domain.templ.GenericEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
public class Task extends GenericEntity {
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
