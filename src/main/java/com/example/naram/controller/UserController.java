package com.example.naram.controller;

import com.example.naram.domain.dto.UserDto;
import com.example.naram.domain.vo.UserLoginVo;
import com.example.naram.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //  로그인 POST 매핑
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto rq){
        UserDto userDto=null;

        String userId = rq.getUserId();
        String userPassword = rq.getUserPassword();
        log.info(userId);
        log.info(userPassword);

        try {
            userDto = userService.login(userId, userPassword);
            log.info("로그인 성공====================== userDto{}",userDto);

            UserLoginVo userLoginVo = new UserLoginVo();
            userLoginVo.setUserNumber(userDto.getUserNumber());
            userLoginVo.setUserId(userDto.getUserId());
            userLoginVo.setUserName(userDto.getUserName());
            log.info("로그인 정보 =====================  userLoginVo{}",userLoginVo);
            return ResponseEntity.ok(userLoginVo);
        } catch (Exception e) {
            log.info("로그인 실패 로그====================== userDto{}",userDto);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
}
