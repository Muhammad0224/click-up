package uz.pdp.clickup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickup.domain.templ.GenericEntity;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
public class Space extends GenericEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    private Workspace workspace;

    @Column
    private String initialLetter;

    @ManyToOne
    private Icon icon;

    @OneToOne
    private Attachment avatar;

    @ManyToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;
}