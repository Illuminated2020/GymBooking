package com.lsz.my_poj.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsz.my_poj.entity.Admin;
import com.lsz.my_poj.mapper.AdminMapper;
import com.lsz.my_poj.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp extends ServiceImpl<AdminMapper, Admin> implements AdminService{
}
