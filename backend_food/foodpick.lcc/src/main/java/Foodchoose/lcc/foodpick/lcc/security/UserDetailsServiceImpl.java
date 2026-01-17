package Foodchoose.lcc.foodpick.lcc.security;


import Foodchoose.lcc.foodpick.lcc.entity.User;
import Foodchoose.lcc.foodpick.lcc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserDetailsServiceImpl - Implementation của Spring Security UserDetailsService
 *
 * Service này được Spring Security sử dụng để load thông tin user từ database
 * trong quá trình authentication
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Load user by email (username trong Spring Security)
     * Method này được gọi tự động bởi Spring Security khi user login
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user với email: " + email));

        // Convert User entity sang UserDetailsImpl
        return UserDetailsImpl.build(user);
    }
}
