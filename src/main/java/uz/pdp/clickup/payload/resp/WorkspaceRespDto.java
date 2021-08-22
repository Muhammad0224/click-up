package uz.pdp.clickup.payload.resp;


import lombok.*;
import uz.pdp.clickup.domain.Attachment;
import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.domain.templ.AbsLongEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceRespDto extends AbsLongEntity {
    private Long id;

    private String name;

    private String color;

    private String owner;

    private String initialLetter;

    private Timestamp createdAt;
}
