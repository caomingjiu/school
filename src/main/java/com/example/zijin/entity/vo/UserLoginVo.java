package com.example.zijin.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Tao
 * @Date: 2021-11-22 10:27
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    private String userAccountId;
    private String userAccount;
    private String userName;
    private String nickname;
    private String jobNumber;
    private String avatar;
    private String phoneNumber;
    private Integer clazzId;
    private String cardId;
    private String address;
}
