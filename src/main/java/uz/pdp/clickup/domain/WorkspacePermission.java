package uz.pdp.clickup.domain;

import lombok.*;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;
import uz.pdp.clickup.enums.WorkspacePermissionName;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspacePermission extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkspaceRole workspaceRole;//O'RINBOSAR

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName permission;//add member,remove member
}
