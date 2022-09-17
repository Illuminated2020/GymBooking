package com.lsz.my_poj.service.imp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsz.my_poj.entity.Blog;
import com.lsz.my_poj.mapper.BlogMapper;
import com.lsz.my_poj.service.BlogService;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImp extends ServiceImpl<BlogMapper, Blog> implements BlogService {
}
