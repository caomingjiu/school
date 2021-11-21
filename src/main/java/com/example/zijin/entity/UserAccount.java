package com.example.zijin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;

@Data
@TableName("sys_user_account")
public class UserAccount {
    @TableField(value = "user_account_id")
    private String userAccountId;

    @TableField(value = "user_account")
    private String userAccount;

    @TableField(value = "user_password")
    private String userPassword;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "job_number")
    private String jobNumber;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "role")
    private String role;

    @TableField(value = "phone_number")
    private String phoneNumber;

    @TableId(value = "status")
    private boolean status;

    @TableField(value = "create_time")
    private Timestamp createTime;

    @TableField(value = "update_time")
    private Timestamp updateTime;

    @TableId(value = "delete_flag")
    private boolean deleteFlag;

    @TableId(value = "clazz_id")
    private Integer clazzId;

    @TableField(value = "card_id")
    private String cardId;

    @TableField(value = "gender")
    private String gender;

    @TableField(value = "address")
    private String address;

}
