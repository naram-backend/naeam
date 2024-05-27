package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class NoticeDetailVo {
    private Long noticeNumber;
    private Long userNumber;
    private String userName;
    private String noticeTitle;
    private String noticeContent;
    private String noticeRegisterDate;
    private String fileName;
    private String fileUrl;
    private boolean important;
    private boolean hiring;
    private int noticeView;
}
