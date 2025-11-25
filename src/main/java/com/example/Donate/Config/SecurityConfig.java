package com.example.Donate.Config;

import com.example.Donate.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.Donate.Repository")
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll() // ✅ thêm /login, Cho phép các trang công khai
//                        .requestMatchers("/admin/**").hasRole("ADMIN")   // Chỉ cho ADMIN vào trang quản trị
//                        .requestMatchers("/profile", "/donate").authenticated()    // ✅ Chỉ người đã đăng nhập mới vào được /profile
//                        .anyRequest().authenticated()   // Các request khác cũng yêu cầu đăng nhập
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")        // ✅ custom login page
//                        .loginProcessingUrl("/login") // ✅ nơi form login gửi tới (mặc định Spring xử lý)
//                        .defaultSuccessUrl("/", true) // ✅ sau khi login thành công
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//
//
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/profile", "/donate").authenticated()
                        .requestMatchers("/profile/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            // ✅ Lấy role của người đăng nhập
                            String role = authentication.getAuthorities().toString();

                            if (role.contains("ADMIN")) {
                                response.sendRedirect("/admin");
                            } else {
                                response.sendRedirect("/home");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

}
