package uz.pdp.clickup.payload.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagRespDto {
    private String name;

    private String color;

    private Long workspaceId;
}
