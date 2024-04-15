package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class NtUploadVo {
    private Long userNumber;
    private String noticeTitle;
    private String noticeContent;
    private String fileName;
    private String fileData;
}
