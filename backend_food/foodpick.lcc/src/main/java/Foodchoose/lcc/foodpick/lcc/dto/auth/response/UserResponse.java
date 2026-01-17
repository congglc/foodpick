package Foodchoose.lcc.foodpick.lcc.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String role;
    private Boolean active;
    private Boolean verified;
}
