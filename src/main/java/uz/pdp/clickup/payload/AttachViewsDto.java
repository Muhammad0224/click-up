package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class AttachViewsDto {
   private List<Long> views;
}
