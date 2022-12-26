package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.entity.User;
import com.lsz.my_poj.util.CheckCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/checkcode")
@CrossOrigin
public class CheckCodeController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public void CheckCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //生成验证码
        ServletOutputStream os=response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);
        System.out.println(checkCode);
        //将生成的验证码缓存到Redis中
        redisTemplate.opsForValue().set("code",checkCode,5, TimeUnit.MINUTES);
    }
}
