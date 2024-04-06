package com.example.naram.service;

import com.example.naram.domain.dto.UserDto;
import com.example.naram.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
//    로그인
    @Transactional
    public UserDto login(String userId, String userPassword){
        log.info("===============로그인 서비스 실행 !!");
        UserDto userDto = userMapper.login(userId, userPassword);
        log.info("로그인 서비스 결과: {}", userDto);
        if (userDto == null) {
            throw new IllegalArgumentException("로그인 서비스 실패!!");
        }
        return userDto;
    }

//    아이디찾기
    public String findId(String userEmail, String userName){
        log.info("아이디찾기 서비스 진입");
        String userId = "";
        userId=userMapper.findId(userEmail,userName);
        log.info("찾은 아이디 userId={}",userId);

        if(userId==null){
            throw new IllegalArgumentException("아이디를 찾을수 없습니다.");
        }
        return userId;
    }

//     비밀번호 찾기
    public UserDto findPw(String userId, String userEmail, String userName){
        log.info("비밀번호 찾기 서비스 진입");
        UserDto userDto = userMapper.findPw(userId,userEmail,userName);

        if(userDto == null){
            throw new IllegalArgumentException("올바른 계정정보를 입력해주세요");
        }
        return userDto;
    }

//    비밀번호 확인
    public String checkPw(Long userNumber){
        return userMapper.checkPw(userNumber);
    }

//    비밀번호 재설정
    public void updatePw(Long userNumber,String newPassword) throws Exception{
        try {
            // 비밀번호 업데이트 로직
            userMapper.updatePw(userNumber, newPassword);
        } catch (Exception e) {
            // 예외 발생 시 로그 기록
            log.error("비밀번호 변경 중 오류 발생: {}", e.getMessage());
            // 예외를 던져서 호출한 쪽에서 처리하도록 함
            throw new Exception("비밀번호 변경에 실패했습니다.");
        }
    }
}
