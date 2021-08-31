package uz.pdp.clickup.payload.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ViewRespDto {
    private String name;

    private Long iconId;
}
