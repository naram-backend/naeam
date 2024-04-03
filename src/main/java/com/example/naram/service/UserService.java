package com.example.naram.service;

import com.example.naram.domain.dto.UserDto;
import com.example.naram.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
