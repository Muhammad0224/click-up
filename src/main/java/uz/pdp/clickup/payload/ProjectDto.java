package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.clickup.enums.AccessType;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ProjectDto {
    @NotNull
    private String name;

    private String color;

    @NotNull
    private UUID spaceId;

    @NotNull
    private AccessType accessType;

    @NotNull
    private List<String> lists;
}
