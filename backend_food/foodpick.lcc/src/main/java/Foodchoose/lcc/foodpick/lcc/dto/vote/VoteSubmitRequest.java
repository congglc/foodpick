package Foodchoose.lcc.foodpick.lcc.dto.vote;

import lombok.Data;

@Data
public class VoteSubmitRequest {
    private Long voteOptionId;
    private String voterName;
}
