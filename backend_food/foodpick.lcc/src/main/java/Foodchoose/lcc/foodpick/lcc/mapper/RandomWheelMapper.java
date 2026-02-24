package Foodchoose.lcc.foodpick.lcc.mapper;

import Foodchoose.lcc.foodpick.lcc.dto.randomwheel.RandomWheelRequest;
import Foodchoose.lcc.foodpick.lcc.dto.randomwheel.RandomWheelResponse;
import Foodchoose.lcc.foodpick.lcc.entity.RandomWheel;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {FoodMapper.class}
)
public interface RandomWheelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "foods", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    RandomWheel requestToEntity(RandomWheelRequest request);

    @Mapping(target = "creatorName", source = "creator.fullName")
    @Mapping(target = "foods", source = "foods")
    RandomWheelResponse entityToResponse(RandomWheel randomWheel);
    
    List<RandomWheelResponse> entitiesToResponses(List<RandomWheel> randomWheels);
}
