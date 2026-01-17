package Foodchoose.lcc.foodpick.lcc.service;

import Foodchoose.lcc.foodpick.lcc.dto.auth.request.ChangePasswordRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.request.LoginRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.request.RegisterRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.request.UpdateProfileRequest;
import Foodchoose.lcc.foodpick.lcc.dto.auth.response.AuthResponse;
import Foodchoose.lcc.foodpick.lcc.dto.auth.response.UserResponse;
import Foodchoose.lcc.foodpick.lcc.entity.User;
import Foodchoose.lcc.foodpick.lcc.enums.UserRole;
import Foodchoose.lcc.foodpick.lcc.exception.ResourceNotFoundException;
import Foodchoose.lcc.foodpick.lcc.mapper.UserMapper;
import Foodchoose.lcc.foodpick.lcc.repository.UserRepository;
import Foodchoose.lcc.foodpick.lcc.security.JwtUtil;
import Foodchoose.lcc.foodpick.lcc.security.UserDetailsImpl;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponse register(RegisterRequest request) throws BadRequestException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email đăng ký đã tồn tai");
        }

        User user = userMapper.registerRequestToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user.setActive(true);
        user.setVerified(false);

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail());

        return new  AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFullName(),
                savedUser.getRole().name()

        );
    }

    @Transactional
    public AuthResponse login(LoginRequest request) throws BadRequestException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateTokenFromAuth(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new  AuthResponse(
                token,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFullName(),
                userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_","")
        );

    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        return userMapper.userToUserResponse(user);

    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) throws BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new BadRequestException("Mat khau cu khong dung");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

    }

    @Transactional
    public UserResponse updateProfile(UpdateProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        // Update các field từ request
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.userToUserResponse(updatedUser);
    }

}
