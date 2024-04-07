package com.example.naram.mapper;

import com.example.naram.domain.dto.UserAddDto;
import com.example.naram.domain.dto.UserDto;
import com.example.naram.domain.vo.CheckPwVo;
import com.example.naram.domain.vo.MyPageVo;
import com.example.naram.domain.vo.UserLoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
//  로그인
    public UserLoginVo login(@Param("userId")String userId, @Param("userPassword")String userPassword);
//  아이디찾기
    public String findId(@Param("userEmail")String userEmail, @Param("userName")String userName);
//  비밀번호 찾기
    public UserDto findPw(@Param("userId")String userId, @Param("userEmail")String userEmail, @Param("userName")String userName);
//  비밀번호 확인
    public String checkPw(@Param("userNumber")Long userNumber);
//  비밀번호 재설정
    public void updatePw(@Param("userNumber")Long userNumber, @Param("newPassword")String newPassword);
//  회원가입
    public void userJoin(UserDto userDto);
//  회원 추가정보
    public void userAdd(UserAddDto userAddDto);
//  아이디 중복확인
    public int checkId(String userId);
//  이메일 중복확인
    public int checkEmail(String userEmail);
//  닉네임 중복확인
    public int checkNickname(String nickname);
//  마이페이지
    public MyPageVo myPage(Long userNumber);
}
