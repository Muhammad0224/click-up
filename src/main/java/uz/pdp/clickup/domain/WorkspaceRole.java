package uz.pdp.clickup.domain;


import lombok.*;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;
import uz.pdp.clickup.enums.WorkspaceRoleName;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceRole extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Workspace workspace;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName extendsRole;
}
