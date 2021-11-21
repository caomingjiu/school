package com.example.zijin.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zijin.entity.Test;
import com.example.zijin.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService extends ServiceImpl<TestMapper,Test> {
    @Autowired
    private  TestMapper testMapper;
    public List<Test> testDemo(){
        List<Test> test = lambdaQuery().select().list();
        return test;
    }

    public Test selectIdTest(int id){
       Test test = super.baseMapper.selectById(1);
        return  test;
    }
}
