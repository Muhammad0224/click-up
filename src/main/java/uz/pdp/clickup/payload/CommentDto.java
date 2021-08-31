package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CommentDto {
    private String text;

    private UUID taskId;
}
