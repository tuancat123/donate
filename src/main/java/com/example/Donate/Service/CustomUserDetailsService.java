package com.example.Donate.Service;

import com.example.Donate.Entity.User;
import com.example.Donate.Repository.UserRepository;
import com.example.Donate.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + username));
        return new CustomUserDetails(user);
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword()) // phải là password mã hóa
//                .roles(user.getRole().name())
//                .build();
    }
}
