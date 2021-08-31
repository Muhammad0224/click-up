package uz.pdp.clickup.payload.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ChecklistItemRespDto {
    private String name;

    private UUID checklistId;

    private boolean resolved;
}
