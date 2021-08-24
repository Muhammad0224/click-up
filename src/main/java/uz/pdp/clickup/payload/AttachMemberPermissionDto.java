package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.clickup.enums.WorkspacePermissionName;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
public class AttachMemberPermissionDto {
    // example {"f8bec6a4-04cb-11ec-9a03-0242ac130003": ["CAN_CREATE_SPACES"]}
    private Map<UUID, List<WorkspacePermissionName>> memberPermission;
}
