package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class QnaUploadVo {
    private Long userNumber;
    private String qnaTitle;
    private String qnaContent;
    private String fileName;
    private String fileData;
}
