package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class EditWorkspacePermissionDto {
    @NotNull
    private Long workspaceId;

    @NotNull
    private UUID roleId;

    List<String> permissions;
}
