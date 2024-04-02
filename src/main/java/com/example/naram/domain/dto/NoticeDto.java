package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class NoticeDto {
    private Long noticeNumber;
    private Long userNumber;
    private String noticeTitle;
    private String noticeContent;
    private String noticeRegisterDate;
    private boolean important;
    private boolean hiring;
    private int noticeView;
}
