package Foodchoose.lcc.foodpick.lcc.dto.vote;

import Foodchoose.lcc.foodpick.lcc.dto.food.FoodResponse;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VoteResponse {
    private Long id;
    private String title;
    private String description;
    private String creatorName;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private List<VoteOptionResponse> options;
    private Long totalVotes;
}
