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
import com.example.zijin.entity.vo.UserLoginVo;
import com.example.zijin.mapper.UserAccountMapper;
import com.example.zijin.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService extends ServiceImpl<UserAccountMapper, UserAccount> {
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private RedisService redisService;

    public UserAccount selectIdUserAccount(String id) {
        UserAccount userAccount = userAccountMapper.selectUserById(id);
        return userAccount;
    }

    public int updateUserAvatar(String avatar, String id) {
        userAccountMapper.updateUserAvatar(avatar, id);
        return 0;
    }

    public int updateUser(String nickname, String gender, String address, String id) {
        userAccountMapper.updateUser(nickname, gender, address, id);
        return 0;
    }

    public void login(LoginDto loginDto) {
        List<UserAccount> userAccountList = super.baseMapper.selectList(null);
        int i = 0;
//        Result result=new Result();
//        for(;i<userAccountList.size();i++){
//            if(loginDto.getAccount().equals(userAccountList.get(i).getUserAccount())){
//                if(loginDto.getPassword().equals(userAccountList.get(i).getUserPassword())){
//                    result.setCode(200);
//                    result.setResult("????????????");
//                    Map<String, Object> userMap = new HashMap<String, Object>();
//                    userMap.put("userId",userAccountList.get(i).getUserAccountId());
//                    result.setData(userMap);
//                    return result;
//                }
//            }
//        }
//        if(i>=userAccountList.size()){
//            result.setCode(2001);
//            result.setResult("????????????");
//            return result;
//        }
//        return result;
    }

    public Result sendSms(SignDto signDto) {
        String mobile = signDto.getMobile();
        System.out.println("??????????????????mobile:" + mobile);
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
        request.putQueryParameter("SignName", "????????????");
        request.putQueryParameter("TemplateCode", "SMS_179216037");
        String verifyCode = StringUtil.getVerifyCode();
        request.putQueryParameter("TemplateParam", "{\"code\":" + verifyCode + "}");
        CommonResponse response;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            log.error("??????????????????");
            return Result.failure(ResultCode.SMS_ERROR);
        }
        //resData?????????{"Message":"OK","RequestId":"0F3A84A6-55CA-4984-962D-F6F54281303E","BizId":"300504175696737408^0","Code":"OK"}
        String resData = response.getData();
        //????????????JSON???????????????JSON??????
        JSONObject jsonObject = JSONObject.parseObject(resData);
        if ("OK".equals(jsonObject.get("Code"))) {
            System.out.println("????????????" + verifyCode);
            System.out.println("????????????" + mobile);
            //??????redis???5????????????
            redisService.set(mobile, verifyCode, 5L);
            return Result.success(verifyCode);
        } else {
            return Result.failure(ResultCode.SMS_ERROR);
        }
    }

    public Result checkSms(SignDto signDto) {
        String mobile = signDto.getMobile();
        String sms = signDto.getSms();
        String correctSms = redisService.getValue(mobile, String.class);
        if (correctSms != null) {
            //???????????????????????????????????????redis??????????????????????????????
            if (correctSms.equals(sms)) {
                return Result.success("???????????????");
            } else {
                //???????????????
                Result.failure(ResultCode.USER_VERIFY_CODE_ERROR);
            }
        }
        //???????????????
        return Result.failure(ResultCode.USER_CODE_TIMEOUT);
    }

    /**
     * ??????????????????
     *
     * @param signDto
     * @return
     */
    public Result sign(SignDto signDto) {
        String mobile = signDto.getMobile();
        // ????????????????????????
        UserAccount user = lambdaQuery().eq(UserAccount::getPhoneNumber, mobile).one();
        if (user != null) {
            //???????????????????????????
            Result result = checkSms(signDto);
            if (result.getCode() == 1) {
                //???????????????
                String token = DigestUtils.sha3_256Hex(user.getUserAccount());
                redisService.set(mobile, token, 60 * 24L);
                UserLoginVo userLoginVo = UserLoginVo.builder()
                        .userAccountId(user.getUserAccountId())
                        .userAccount(user.getUserAccount())
                        .userName(user.getUserName())
                        .nickname(user.getNickname())
                        .jobNumber(user.getJobNumber())
                        .avatar(user.getAvatar())
                        .phoneNumber(user.getPhoneNumber())
                        .clazzId(user.getClazzId())
                        .cardId(user.getCardId())
                        .address(user.getAddress())
                        .build();
                return Result.success(userLoginVo);
            } else {
                return Result.failure(ResultCode.USER_VERIFY_CODE_ERROR);
            }
        } else {
            return Result.failure(ResultCode.USER_NOT_FOUND);
        }
    }

}
