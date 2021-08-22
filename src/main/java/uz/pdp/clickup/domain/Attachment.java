package uz.pdp.clickup.domain;


import lombok.*;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;

import javax.persistence.Entity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AbsUUIDEntity {

    private String name;

    private String originalName;

    private Long size;

    private String contentType;

}
