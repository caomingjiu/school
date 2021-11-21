package com.example.zijin.controller;

import com.example.zijin.entity.UserAccount;
import com.example.zijin.common.Result;
import com.example.zijin.entity.dto.SignDto;
import com.example.zijin.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(value="用户模块", description="用户模块")
@RequestMapping("/user")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;
    @ApiOperation(value="根据id获取用户",notes="根据id获取用户")
    @GetMapping("/{id}")
    public UserAccount getUserId(@PathVariable String id){
        return userAccountService.selectIdUserAccount(id);
    }
//    @ApiOperation(value="用户登录")
//    @PostMapping("/login")
//    public Result userLogin(@RequestBody LoginDto loginDto){
//        return userAccountService.login(loginDto);
//    }

    @ApiOperation(value="修改头像")
    @PostMapping("/update")
    public int updateUserAvatar(@RequestBody UserAccount userAccount){
        return userAccountService.updateUserAvatar(userAccount.getAvatar(),userAccount.getUserAccountId());
    }
    @ApiOperation(value="修改用户信息")
    @PostMapping("/update/info")
    public int updateUser(@RequestBody UserAccount userAccount){
        System.out.println(userAccount.getNickname());
        return userAccountService.updateUser( userAccount.getNickname(),userAccount.getGender(),userAccount.getAddress(),userAccount.getUserAccountId());
    }
    /**
     * 发送手机短信
     * @param signDto
     * @return
     */
    @ApiOperation(value = "通过手机号码发送短信验证码",notes = "")
    @PostMapping(value = "/sms")
    Result getSms(@RequestBody SignDto signDto){
        System.out.println("进入接口");
        return userAccountService.sendSms(signDto);
    }
}
