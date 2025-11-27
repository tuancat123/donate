package com.example.Donate.Service;

import com.example.Donate.Repository.UserRepository;
import com.example.Donate.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // Xác thực email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);

        // mặc định chưa xác thực
        user.setEnabled(false);

        return userRepository.save(user);
    }

    public User findByVerificationCode(String code) {
        return userRepository.findByVerificationCode(code);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    // 🔹 Tìm user theo email (dùng cho chức năng tạo chiến dịch)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
    public void enableUser(Integer id) {
        User user = getUserById(id);
        if (user != null) {
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    public void disableUser(Integer id) {
        User user = getUserById(id);
        if (user != null) {
            user.setEnabled(false);
            userRepository.save(user);
        }
    }



    // Cập nhật thông tin cơ bản
    public void updateProfile(User user, String fullName, String email) {
        user.setFullName(fullName);
        user.setEmail(email);
        userRepository.save(user);
    }

    // Đổi mật khẩu
    public boolean changePassword(User user, String oldPassword, String newPassword) {

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false; // sai mật khẩu cũ
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public long getTotalUsers() {
        return userRepository.count();
    }
}
