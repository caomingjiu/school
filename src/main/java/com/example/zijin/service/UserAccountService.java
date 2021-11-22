package com.example.zijin.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zijin.common.Result;
import com.example.zijin.common.ResultCode;
import com.example.zijin.entity.UserAccount;
import com.example.zijin.entity.dto.LoginDto;
import com.example.zijin.entity.dto.SignDto;
import com.example.zijin.mapper.UserAccountMapper;
import com.example.zijin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService extends ServiceImpl<UserAccountMapper, UserAccount> {
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private RedisService redisService;
    public UserAccount selectIdUserAccount(String id){
        UserAccount userAccount=userAccountMapper.selectUserById(id);
        return userAccount;
    }
    public int updateUserAvatar(String avatar ,String id){
        userAccountMapper.updateUserAvatar(avatar,id);
        return 0;
    }
    public int updateUser(String nickname,String gender,String address,String id){
        userAccountMapper.updateUser(nickname, gender, address, id);
        return 0;
    }
    public void login(LoginDto loginDto){
        List<UserAccount> userAccountList=super.baseMapper.selectList(null);
        int i=0;
//        Result result=new Result();
//        for(;i<userAccountList.size();i++){
//            if(loginDto.getAccount().equals(userAccountList.get(i).getUserAccount())){
//                if(loginDto.getPassword().equals(userAccountList.get(i).getUserPassword())){
//                    result.setCode(200);
//                    result.setResult("登录成功");
//                    Map<String, Object> userMap = new HashMap<String, Object>();
//                    userMap.put("userId",userAccountList.get(i).getUserAccountId());
//                    result.setData(userMap);
//                    return result;
//                }
//            }
//        }
//        if(i>=userAccountList.size()){
//            result.setCode(2001);
//            result.setResult("登录失败");
//            return result;
//        }
//        return result;
    }

    public Result sendSms(SignDto signDto) {
        String mobile = signDto.getMobile();
        System.out.println("进入发送短信mobile:" + mobile);
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-beijing",
                "LTAI4FvqK568YDj7HtsPEZ5d",
                "VMfoqfNr2TpdzQVc9sLIOmSt5f5rhN");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-beijing");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "个人空间");
        request.putQueryParameter("TemplateCode", "SMS_179216037");
        String verifyCode = StringUtil.getVerifyCode();
        request.putQueryParameter("TemplateParam", "{\"code\":" + verifyCode + "}");
        CommonResponse response;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            log.error("短信发送异常");
            return Result.failure(ResultCode.SMS_ERROR);
        }
        //resData样例：{"Message":"OK","RequestId":"0F3A84A6-55CA-4984-962D-F6F54281303E","BizId":"300504175696737408^0","Code":"OK"}
        String resData = response.getData();
        //将返回的JSON字符串转成JSON对象
        JSONObject jsonObject = JSONObject.parseObject(resData);
        if ("OK".equals(jsonObject.get("Code"))) {
            System.out.println("验证码："+verifyCode);
            System.out.println("手机号："+mobile);
            //存入redis，5分钟有效
            redisService.set(mobile, verifyCode, 5L);
            return Result.success(verifyCode);
        } else {
            return Result.failure(ResultCode.SMS_ERROR);
        }
    }
}
