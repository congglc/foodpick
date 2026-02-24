package Foodchoose.lcc.foodpick.lcc.dto.randomwheel;

import lombok.Data;
import java.util.List;

@Data
public class RandomWheelRequest {
    private String title;
    private List<Long> foodIds;
}
