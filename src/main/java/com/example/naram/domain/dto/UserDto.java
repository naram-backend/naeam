package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class UserDto {
    private Long userNumber;
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
    private boolean admin;
}
