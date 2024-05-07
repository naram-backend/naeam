package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class NoticeFileDto {
    private Long noticeFileNumber;
    private Long noticeNumber;
    private String fileUrl;
    private String fileName;
}
