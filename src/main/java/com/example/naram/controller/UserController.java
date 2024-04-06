package com.example.naram.controller;

import com.example.naram.domain.dto.UserDto;
import com.example.naram.domain.vo.UserLoginVo;
import com.example.naram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    private final MailService mailService;

    //  로그인
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
            userLoginVo.setUserEmail(userDto.getUserEmail());
            log.info("로그인 정보 =====================  userLoginVo{}",userLoginVo);
            return ResponseEntity.ok(userLoginVo);
        } catch (Exception e) {
            log.info("로그인 실패 로그====================== userDto{}",userDto);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
    
//    아이디 찾기
    @PostMapping("/findId")
    public ResponseEntity<?> findId(@RequestBody UserDto rq){

        String userEmail = rq.getUserEmail();
        String userName = rq.getUserName();
        log.info(userEmail);
        log.info(userName);

        try{
            String userId = userService.findId(userEmail,userName);
            log.info("아이디 찾기 성공 userId{}",userId);
            return ResponseEntity.ok(userId);
        }catch (Exception e){
            log.info("아이디 찾기 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디를 찾을 수 없습니다.");
        }

    }

//    비밀번호 찾기
    @PostMapping("/findPw")
    public ResponseEntity<?> findPw(@RequestBody UserDto rq){

        log.info("전달받은 id = {}",rq.getUserId());
        log.info("전달받은 name = {}",rq.getUserName());
        log.info("전달받은 email = {}",rq.getUserEmail());

        try{
            UserDto userDto = userService.findPw(rq.getUserId(), rq.getUserEmail(), rq.getUserName());
            log.info("비밀번호 찾기 성공 userDto = {}",userDto);

            return ResponseEntity.ok(userDto);
        }catch (Exception e){
            log.info("비밀번호 찾기 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("올바른 계정정보를 입력해주세요 body");
        }
    }

}
