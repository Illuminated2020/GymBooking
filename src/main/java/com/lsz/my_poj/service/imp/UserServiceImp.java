package com.lsz.my_poj.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsz.my_poj.entity.User;
import com.lsz.my_poj.mapper.UserMapper;
import com.lsz.my_poj.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
}
