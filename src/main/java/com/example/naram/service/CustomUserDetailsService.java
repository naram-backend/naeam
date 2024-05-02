package com.example.naram.service;

import com.example.naram.domain.vo.UserLoginVo;
import com.example.naram.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : {}",username);
        UserLoginVo userLoginVo = userMapper.findByUsername(username);
        log.info("userLoginVo : {}",userLoginVo);
        if(userLoginVo == null){
            throw new UsernameNotFoundException("User not found");
        }
        return userLoginVo;
    }
}
