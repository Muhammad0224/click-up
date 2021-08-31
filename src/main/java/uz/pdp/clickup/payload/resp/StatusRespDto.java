package uz.pdp.clickup.payload.resp;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.clickup.enums.StatusType;

import java.util.UUID;

@Setter
@Getter
public class StatusRespDto {
    private String name;

    private String color;

    private UUID spaceId;

    private UUID projectId;

    private UUID categoryId;

    private StatusType statusType;
}
