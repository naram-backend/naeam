package com.example.naram.service;

import com.example.naram.domain.dto.UserAddDto;
import com.example.naram.domain.dto.UserDto;
import com.example.naram.domain.vo.MyPageVo;
import com.example.naram.domain.vo.UserLoginVo;
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
    public UserLoginVo login(String userId, String userPassword){
        log.info("===============로그인 서비스 실행 !!");
        UserLoginVo userLoginVo = userMapper.login(userId, userPassword);
        log.info("로그인 서비스 결과: {}", userLoginVo);
        if (userLoginVo == null) {
            throw new IllegalArgumentException("로그인 서비스 실패!!");
        }
        return userLoginVo;
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
//    아이디 중복확인
    public int checkId(String userId) throws Exception{
        try {
            log.info("userId : {}",userId);
            log.info("매퍼 : {}",userMapper.checkId(userId));
            return userMapper.checkId(userId);
        } catch (Exception e){
            throw new Exception("에러");
        }
    }
//    이메일 중복확인
    public int checkEmail(String userEmail) throws Exception{
        try {
            log.info("userEmail : {}",userEmail);
            return userMapper.checkEmail(userEmail);
        }catch (Exception e){
        throw new Exception("에러");
        }
    }
//    닉네임 중복확인
    public int checkNickName(String nickname) throws Exception{
        try {
            return userMapper.checkNickname(nickname);
        } catch (Exception e){
            throw new Exception("에러");
        }
    }
//    회원가입
    @Transactional
    public void insertUser (UserDto userDto, UserAddDto userAddDto) throws Exception{
        log.info("회원가입 서비스 진입");
        try {
            log.info("===============");
            log.info("userJoin");
            log.info("userDto : {}",userDto);
            userMapper.userJoin(userDto);
            log.info("userADd");
            userMapper.userAdd(userAddDto);
            log.info("===============");
        } catch (Exception e){
            log.error("회원정보 등록중 오류 발생 : {}",e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new Exception("회원가입 등록 실패");
        }
    }

//    마이페이지
    public MyPageVo myPage (Long userNumber)throws Exception{
        log.info("=========== 마이페이지 서비스 진입 ===========");
        try {
            return userMapper.myPage(userNumber);
        } catch (Exception e){
            throw new Exception("마이페이지 열람 실패!!");
        }
    }









}
