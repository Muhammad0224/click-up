package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickup.domain.templ.GenericEntity;
import uz.pdp.clickup.enums.DependencyType;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
public class TaskHistory extends GenericEntity {
    @ManyToOne
    private Task task;

    @Column
    private String changeFieldName;

    @Column
    private String before;

    @Column
    private String after;

    @Column
    private String data;
}
