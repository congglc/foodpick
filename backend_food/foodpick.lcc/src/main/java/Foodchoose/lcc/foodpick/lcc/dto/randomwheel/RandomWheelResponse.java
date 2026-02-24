package Foodchoose.lcc.foodpick.lcc.dto.randomwheel;

import Foodchoose.lcc.foodpick.lcc.dto.food.FoodResponse;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RandomWheelResponse {
    private Long id;
    private String title;
    private String creatorName;
    private LocalDateTime createdAt;
    private List<FoodResponse> foods;
}
