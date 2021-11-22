package com.example.zijin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.zijin.entity.UserAccount;


/**
 * @author Tao_Dell
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    UserAccount selectUserById(String id);
    int updateUserAvatar(String avatar,String id);
    int updateUser(String nickname,String gender,String address,String id);
}
