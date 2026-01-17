package Foodchoose.lcc.foodpick.lcc.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "email không được để trống")
    @Email(message = "email không hợp lệ")
    private String email;

    @NotBlank(message = "mật khẩu không được để trống")
    @Size(min = 8)
    private String password;

    @NotBlank(message = "User name không được bỏ trống")
    @Size(min = 3, max = 20)
    private String fullName;


}
