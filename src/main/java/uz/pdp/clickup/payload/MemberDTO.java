package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.enums.AddType;

import java.util.UUID;

@Data
public class MemberDTO {
    private UUID id;

    private UUID roleId;

    private AddType addType;//ADD, EDIT, REMOVE
}
