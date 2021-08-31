package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.clickup.domain.Project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Setter
@Getter
public class CategoryDto {
    @NotBlank
    private String name;

    @NotNull
    private UUID projectId;

    @NotNull
    private UUID spaceId;
}
