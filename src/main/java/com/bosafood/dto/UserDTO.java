package com.bosafood.dto;

import com.bosafood.model.UserRole;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole role;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
} 