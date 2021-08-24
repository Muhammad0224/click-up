package uz.pdp.clickup.payload.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.clickup.domain.Attachment;
import uz.pdp.clickup.domain.Icon;
import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.domain.Workspace;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;
import uz.pdp.clickup.enums.AccessType;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRespDto{
    private UUID id;

    private String name;

    private String color;

    private Long workspace;

    private String initialLetter;

    private Long icon;

    private UUID avatar;

    private UUID owner;

    private AccessType accessType;
}
