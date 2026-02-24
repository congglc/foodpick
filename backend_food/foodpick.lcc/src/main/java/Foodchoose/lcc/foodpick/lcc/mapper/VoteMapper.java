package Foodchoose.lcc.foodpick.lcc.mapper;

import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteOptionResponse;
import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteRequest;
import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteResponse;
import Foodchoose.lcc.foodpick.lcc.entity.Vote;
import Foodchoose.lcc.foodpick.lcc.entity.VoteOption;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {FoodMapper.class}
)
public interface VoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "options", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Vote requestToEntity(VoteRequest request);

    @Mapping(target = "creatorName", source = "creator.fullName")
    @Mapping(target = "options", source = "options")
    @Mapping(target = "totalVotes", expression = "java(calculateTotalVotes(vote))")
    VoteResponse entityToResponse(Vote vote);

    VoteOptionResponse optionToOptionResponse(VoteOption option);

    List<VoteOptionResponse> optionsToOptionResponses(List<VoteOption> options);

    default Long calculateTotalVotes(Vote vote) {
        if (vote.getOptions() == null) return 0L;
        return vote.getOptions().stream()
                .mapToLong(option -> option.getVoteCount() != null ? option.getVoteCount() : 0L)
                .sum();
    }
}
