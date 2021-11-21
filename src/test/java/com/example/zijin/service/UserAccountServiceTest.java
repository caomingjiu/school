package com.example.zijin.service;

import com.example.zijin.entity.UserAccount;
import com.example.zijin.entity.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAccountServiceTest {
    @Autowired
    private UserAccountService userAccountService;

    @Test
    void test(){
//        LoginDto loginDto=new LoginDto();
//        loginDto.setAccount("1802333101");
//        loginDto.setPassword("123456");
//        System.out.println(userAccountService.login(loginDto));
        userAccountService.updateUser("3","3","3","1");
    }
}
