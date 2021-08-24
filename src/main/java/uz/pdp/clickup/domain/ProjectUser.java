package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;
import uz.pdp.clickup.enums.WorkspacePermissionName;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProjectUser extends AbsUUIDEntity {
    @ManyToOne
    private User member;

    @ManyToOne
    private Project project;

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName permission;
}
