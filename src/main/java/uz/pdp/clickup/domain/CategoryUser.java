package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickup.domain.templ.AbsLongEntity;
import uz.pdp.clickup.enums.WorkspacePermissionName;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CategoryUser extends AbsLongEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName permission;
}
