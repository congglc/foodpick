package Foodchoose.lcc.foodpick.lcc.controller;


import Foodchoose.lcc.foodpick.lcc.dto.auth.request.ChangePasswordRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.request.LoginRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.request.RegisterRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.request.UpdateProfileRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.response.AuthResponse;
import Foodchoose.lcc.foodpick.lcc.dto.auth.response.UserResponse;
import Foodchoose.lcc.foodpick.lcc.dto.response.MessageResponse;
import Foodchoose.lcc.foodpick.lcc.entity.User;
import Foodchoose.lcc.foodpick.lcc.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)

public class AuthController {

    private final AuthService authService;

    @GetMapping("/")
    public String home() {
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) throws BadRequestException {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) throws BadRequestException {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse response = authService.getCurrentUser();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) throws BadRequestException {
        authService.changePassword(request);
        return ResponseEntity.ok(new MessageResponse("Đổi mật khẩu thành công!"));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        UserResponse response = authService.updateProfile(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Với JWT stateless, logout chủ yếu xử lý ở client-side
        // Client sẽ xóa token khỏi localStorage
        return ResponseEntity.ok(new MessageResponse("Đăng xuất thành công!"));
    }
}

