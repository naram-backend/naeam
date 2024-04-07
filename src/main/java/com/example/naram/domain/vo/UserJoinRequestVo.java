package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class UserJoinRequestVo {
//    회원 기본정보
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String userBirth;
    private boolean userCalendar;
//    회원 추가정보
    private String address;
    private String addressDetail;
    private String addressPost;
    private String phone;
    private boolean gender;
    private String nickname;
}
