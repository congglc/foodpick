package Foodchoose.lcc.foodpick.lcc.security;


import Foodchoose.lcc.foodpick.lcc.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * UserDetailsImpl - Implementation của Spring Security UserDetails
 *
 * Class này wrap User entity và implement UserDetails interface
 * để Spring Security có thể sử dụng thông tin user trong authentication
 */
@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private String id;
    private String email;
    private String password;
    private String fullName;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean active;

    /**
     * Factory method để tạo UserDetailsImpl từ User entity
     */
    public static UserDetailsImpl build(User user) {
        // Tạo authority từ role của user
        // Ví dụ: CUSTOMER -> ROLE_CUSTOMER
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                Collections.singletonList(authority),
                user.isActive()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // Spring Security sử dụng username, nhưng chúng ta dùng email
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}