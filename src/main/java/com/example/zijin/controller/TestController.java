package com.example.zijin.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.zijin.entity.Test;
import com.example.zijin.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(value="swagger测试", description="测试")
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @ApiOperation(value="获取用户",notes="获取用户")
    @GetMapping
    public List<Test> getTestValue(){
        return testService.testDemo();
    }

    @ApiOperation(value="根据id获取用户",notes="根据id获取用户")
    @GetMapping("/{id}")
    public Test getIdTest(@PathVariable Integer id){
        return testService.selectIdTest(id);
    }
}
