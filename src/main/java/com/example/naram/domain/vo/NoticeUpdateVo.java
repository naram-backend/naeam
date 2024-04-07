package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class NoticeUpdateVo {
    private Long noticeNumber;
    private String noticeTitle;
    private String noticeContent;
}
