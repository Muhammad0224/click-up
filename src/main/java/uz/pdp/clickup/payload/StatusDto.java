package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Setter
@Getter
public class StatusDto {
    @NotBlank
    private String name;

    @NotNull
    private String color;

    @NotNull
    private UUID spaceId;

    @NotNull
    private UUID projectId;

    @NotNull
    private UUID categoryId;
}
