package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class QnaDetailVo {
    private Long qnaNumber;
    private Long userNumber;
    private String userName;
    private String qnaTitle;
    private String qnaContent;
    private String qnaRegisterDate;
    private boolean qnaPrivate;
    private boolean qnaAnswer;
    private int qnaView;
}
