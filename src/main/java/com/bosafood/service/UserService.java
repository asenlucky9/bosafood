package com.bosafood.service;

import com.bosafood.dto.ActivityLogDTO;
import com.bosafood.dto.UserDTO;
import com.bosafood.dto.UserDetailsDTO;
import com.bosafood.mapper.UserMapper;
import com.bosafood.model.User;
import com.bosafood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Long getTotalUsers() {
        return userRepository.count();
    }

    public Long getActiveUsers() {
        return userRepository.countByActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO getUserDetailsWithOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserDetailsDTO details = new UserDetailsDTO();
        details.setId(user.getId());
        details.setEmail(user.getEmail());
        details.setFirstName(user.getFirstName());
        details.setLastName(user.getLastName());
        details.setActive(user.isActive());
        // Add order history and other details
        
        return details;
    }

    public UserDTO updateUserStatus(Long userId, Boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(active);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public List<ActivityLogDTO> getActivityLogs(String startDate, String endDate) {
        OffsetDateTime start = startDate != null ? OffsetDateTime.parse(startDate) : null;
        OffsetDateTime end = endDate != null ? OffsetDateTime.parse(endDate) : null;
        
        // Implementation would typically involve a separate ActivityLog entity and repository
        // For now, returning an empty list
        return List.of();
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        userMapper.updateEntity(userDTO, user);
        
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
} 