package com.example.zijin.mapper;

import com.example.zijin.entity.UserAccount;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAccountMapperTest {
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Test
    void test(){
      UserAccount userAccount= userAccountMapper.selectUserById("1");
      System.out.println(userAccount);
//        userAccountMapper.updateUser("君彦斌","1","1","1");
    }
}