package Foodchoose.lcc.foodpick.lcc.dto.vote;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VoteRequest {
    private String title;
    private String description;
    private LocalDateTime expiresAt;
    private List<Long> foodIds;
}
