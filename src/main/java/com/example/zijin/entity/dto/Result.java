package com.example.zijin.entity.dto;

import com.example.zijin.entity.UserAccount;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Result {
    private Integer code;
    private String result;
    private Map<String,Object> data;
}
