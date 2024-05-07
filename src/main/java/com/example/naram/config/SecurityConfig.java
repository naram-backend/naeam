package com.example.naram.config;

import com.example.naram.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**","/pr/**","/notice/**","/test/loginT","/user/join","/user/checkPw"
                                                ,"/user/findPw","/user/findId","/user/checkId","user/checkEmail"
                                                ,"/user/checkNickname","/user/verifyCode"
                                                            ).permitAll()
                        .requestMatchers("/test/admin","/test/boardTest").hasRole("ADMIN")
                        .requestMatchers("/user/myPage","/user/info","/user/updatePw"
                                                            ).hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );
        http
                .formLogin((auth) -> auth.loginPage("/test/loginT")
                    .loginProcessingUrl("/login")
                    .successHandler(customAuthenticationSuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
                    .permitAll()
                );

        http
                .csrf((auth) -> auth.disable());
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/", "/login").permitAll()
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
//                        .anyRequest().authenticated()
//                );
        /* 테스트 끝나면 hasRole("ADMIN") 안에다가 집어넣기
        ,"/notice/upload","/notice/noticeConfig"
                ,"/notice/delete"
        */
        return http.build();
    }

}
