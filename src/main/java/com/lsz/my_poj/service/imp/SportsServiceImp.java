package com.lsz.my_poj.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsz.my_poj.entity.Sports;
import com.lsz.my_poj.mapper.SportsMapper;
import com.lsz.my_poj.service.SportsService;
import org.springframework.stereotype.Service;

@Service
public class SportsServiceImp extends ServiceImpl<SportsMapper, Sports> implements SportsService {
}
