package uz.pdp.clickup.payload.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.clickup.enums.SystemRoleName;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDto {
    private UUID id;

    private String fullName;

    private String email;

    private String initialLetter;

    private String color;

    private SystemRoleName systemRoleName;

    private Timestamp createdAt;
}
