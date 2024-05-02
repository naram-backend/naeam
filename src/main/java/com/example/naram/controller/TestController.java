package com.example.naram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test/*")
public class TestController {

    @GetMapping
    public String loginT(){
        return "test/loginT";
    }

    @GetMapping("/admin")
    public String admin(){return "test/admin";}

    @GetMapping("/findPw")
    public String test(){
        return "test/findPw";
    }

    @GetMapping("/boardTest")
    public String boardTest(){
        return "test/boardTest";
    }


}
