package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class QnaCommentDto {
    private Long qnaCommentNumber;
    private Long userNumber;
    private Long qnaNumber;
    private String qnaCommentContent;
    private String qnaCommentRegisterDate;
}
