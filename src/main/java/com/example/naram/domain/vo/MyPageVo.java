package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class MyPageVo {
    private Long userNumber;
    private String userId;
    private String userName;
    private String userEmail;
    private String userBirth;
    private boolean userCalendar;
    private String address;
    private String addressDetail;
    private String addressPost;
    private String phone;
    private boolean gender;
    private String nickname;
}
