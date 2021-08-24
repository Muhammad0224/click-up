package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.clickup.enums.AccessType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class SpaceDto {
    @NotBlank
    private String name;

    @NotNull
    private String color;

    @NotNull
    private Long workspaceId;

    private UUID avatarId;

    private Long iconId;

    private AccessType accessType; // public, private

    private List<UUID> members;
}
