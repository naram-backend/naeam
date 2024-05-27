package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class QnaFileDto {
    private Long qnaFileNumber;
    private Long qnaNumber;
    private String fileUrl;
    private String fileName;
}
