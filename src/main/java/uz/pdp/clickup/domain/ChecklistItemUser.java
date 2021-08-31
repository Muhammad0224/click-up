package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.clickup.domain.templ.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChecklistItemUser extends AbsUUIDEntity {
    @ManyToOne
    private CheckListItem checkListItem;

    @ManyToOne
    private User user;
}
