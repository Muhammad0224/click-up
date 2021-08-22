package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TimeTracked extends AbsUUIDEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Task task;

    @Column
    private Timestamp startedAt;

    @Column
    private Timestamp stoppedAt;
}
