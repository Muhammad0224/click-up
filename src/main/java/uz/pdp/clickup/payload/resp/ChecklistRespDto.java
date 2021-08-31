package uz.pdp.clickup.payload.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ChecklistRespDto {
    private String name;

    private UUID taskId;
}
