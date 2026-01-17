package Foodchoose.lcc.foodpick.lcc.mapper;

import Foodchoose.lcc.foodpick.lcc.dto.auth.request.RegisterRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.response.UserResponse;
import Foodchoose.lcc.foodpick.lcc.entity.User;
import Foodchoose.lcc.foodpick.lcc.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User registerRequestToUser(RegisterRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setId(java.util.UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        return user;
    }

    public UserResponse userToUserResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setRole(user.getRole() != null ? user.getRole().name() : null);
        response.setActive(user.isActive());
        response.setVerified(user.isVerified());
        return response;
    }

    public void updateUserFromRequest(RegisterRequest request, User user) {
        if (request == null || user == null) {
            return;
        }
        // Implement if needed
    }
}