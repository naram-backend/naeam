package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class UserAddDto {
    private Long userAddNumber;
    private Long userNumber;
    private String address;
    private String addressDetail;
    private String addressPost;
    private String phone;
    private boolean gender;
    private String nickname;
}
