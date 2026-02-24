package Foodchoose.lcc.foodpick.lcc.dto.vote;

import Foodchoose.lcc.foodpick.lcc.dto.food.FoodResponse;
import lombok.Data;

@Data
public class VoteOptionResponse {
    private Long id;
    private FoodResponse food;
    private Long voteCount;
}
