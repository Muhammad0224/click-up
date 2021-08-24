package uz.pdp.clickup.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AttachClickAppDto {
   private List<Long> clickApps;
}
