package Foodchoose.lcc.foodpick.lcc.mapper;

import Foodchoose.lcc.foodpick.lcc.dto.auth.request.RegisterRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.response.UserResponse;
import Foodchoose.lcc.foodpick.lcc.entity.User;
import Foodchoose.lcc.foodpick.lcc.enums.UserRole;
import org.mapstruct.*;
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "verified", constant = "false")
    @Mapping(target = "role", constant = "CUSTOMER")
    User registerRequestToUser(RegisterRequest request);

    UserResponse userToUserResponse(User user);

    void updateUserFromRequest(RegisterRequest request, @MappingTarget User user);
}