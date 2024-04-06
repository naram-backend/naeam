package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class CheckPwVo {
    private Long userNumber;
    private String newPassword;
}
