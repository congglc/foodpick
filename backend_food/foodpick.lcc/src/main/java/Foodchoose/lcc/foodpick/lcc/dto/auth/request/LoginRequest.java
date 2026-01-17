package Foodchoose.lcc.foodpick.lcc.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "email không hợp lệ")
    @NotBlank(message = "email không được để trống")
    private String email;

    @NotBlank(message = "mật khẩu không được để trống")
    private String password;
}
