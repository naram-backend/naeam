package com.example.naram.controller;

import com.example.naram.domain.dto.UserDto;
import com.example.naram.domain.vo.CheckPwVo;
import com.example.naram.domain.vo.UserLoginVo;
import com.example.naram.domain.vo.VerificationVo;
import com.example.naram.service.MailService;
import com.example.naram.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MailService mailService;

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \" 로그인 실패 \"}");
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \" 아이디를 찾을 수 없습니다. \"}");
        }

    }

//    비밀번호 찾기
    @PostMapping("/findPw")
    public ResponseEntity<?> findPw(@RequestBody UserDto rq, HttpSession session){

        log.info("전달받은 id = {}",rq.getUserId());
        log.info("전달받은 name = {}",rq.getUserName());
        log.info("전달받은 email = {}",rq.getUserEmail());

        try{
            UserDto userDto = userService.findPw(rq.getUserId(), rq.getUserEmail(), rq.getUserName());
            log.info("비밀번호 찾기 성공 userDto = {}",userDto);
            int num = mailService.sendMail(userDto.getUserEmail());
            log.info("인증번호 @@@@ num{} ",num);
            session.setAttribute("code",num);
            return ResponseEntity.ok(userDto.getUserNumber());
        }catch (Exception e){
            log.info("비밀번호 찾기 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \" 올바른 계정정보를 입력해주세요 \"}");
        }
    }

//    인증번호 확인
    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationVo verification, HttpSession session) {
        // 세션에서 저장된 인증번호 가져오기
        Integer storedCode = (Integer) session.getAttribute("code"); // 세션에 저장된 값은 문자열로 가져옴
        Integer inputCode = Integer.parseInt(verification.getVerificationCode());

        log.info("사용자가 입력한 인증번호 = {}", verification.getVerificationCode());
        log.info("세션에 저장된 인증번호 = {}", storedCode);
        if (storedCode != null && storedCode.equals(inputCode)) {
            // 인증번호가 일치하는 경우
            log.info("일치합니다");
            return ResponseEntity.ok().body("{\"valid\": true}");
        } else {
            // 인증번호가 일치하지 않는 경우
            log.info("불일치합니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"valid\": false}");
        }
    }

//    이전 비밀번호와 new 비밀번호 비교
    @PostMapping("/checkPw")
    public ResponseEntity<?> checkPw(@RequestBody CheckPwVo cp){

        String currentPassword = userService.checkPw(cp.getUserNumber());
        String newPassword = cp.getNewPassword();


        if(currentPassword.equals(newPassword)){
            return ResponseEntity.ok("{\"message\": \"이전 비밀번호와 같음\"}");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"사용 가능한 비밀번호\"}");
        }
    }

//    비밀번호 재설정
    @PostMapping("/updatePw")
    public ResponseEntity<?> updatePw(@RequestBody CheckPwVo cp){
        log.info("======userNumber : {}",cp.getUserNumber());
        log.info("======newPassword : {}",cp.getNewPassword());

        try {
            userService.updatePw(cp.getUserNumber(), cp.getNewPassword());
            return ResponseEntity.ok("{\"message\": \"비밀번호가 변경되었습니다.\"}");
        } catch (Exception e) {
            log.error("비밀번호 변경 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"비밀번호 변경에 실패했습니다.\"}");
        }
    }
}
