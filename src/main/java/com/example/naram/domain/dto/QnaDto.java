package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class QnaDto {
    private Long qnaNumber;
    private Long userNumber;
    private String qnaTitle;
    private String qnaContent;
    private String qnaRegisterDate;
    private boolean qnaPrivate;
    private boolean qnaAnswer;
}
