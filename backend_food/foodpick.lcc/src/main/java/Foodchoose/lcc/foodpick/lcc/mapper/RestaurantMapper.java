package Foodchoose.lcc.foodpick.lcc.mapper;

import Foodchoose.lcc.foodpick.lcc.dto.restaurant.RestaurantRequest;
import Foodchoose.lcc.foodpick.lcc.dto.restaurant.RestaurantResponse;
import Foodchoose.lcc.foodpick.lcc.entity.Restaurant;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "foods", ignore = true)
    Restaurant requestToEntity(RestaurantRequest request);

    RestaurantResponse entityToResponse(Restaurant restaurant);

    List<RestaurantResponse> entitiesToResponses(List<Restaurant> restaurants);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "foods", ignore = true)
    void updateEntityFromRequest(RestaurantRequest request, @MappingTarget Restaurant restaurant);
}
