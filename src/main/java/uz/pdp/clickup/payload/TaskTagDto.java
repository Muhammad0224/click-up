package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Setter
@Getter
public class TaskTagDto {
    @NotNull
    private String name;

    private String color;

    @NotNull
    private Long workspaceId;

    @NotNull
    private UUID taskId;
}
