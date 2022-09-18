package com.lsz.my_poj.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsz.my_poj.entity.Comment;
import com.lsz.my_poj.mapper.CommentMapper;
import com.lsz.my_poj.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
