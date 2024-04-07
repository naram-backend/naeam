package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class UserLoginVo {
    private Long userNumber;
    private String userId;
    private String userName;
    private String userEmail;
    private String nickname;
    private boolean adminCheck;
}
