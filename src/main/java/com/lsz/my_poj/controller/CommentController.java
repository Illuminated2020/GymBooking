package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.dto.BlogDto;
import com.lsz.my_poj.dto.CommentDto;
import com.lsz.my_poj.entity.Blog;
import com.lsz.my_poj.entity.Comment;
import com.lsz.my_poj.entity.User;
import com.lsz.my_poj.service.CommentService;
import com.lsz.my_poj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;
    @PostMapping
    public R add(@RequestBody Comment comment){
        log.info(comment.toString());
        commentService.save(comment);
        return R.success('1');
    }

    @GetMapping
    public R<List<CommentDto>> get(@RequestParam Long id){
        log.info(id.toString());
        List<CommentDto> listDto = null;
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getBlogId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = commentService.list(queryWrapper);

        listDto = list.stream().map((item) -> {//item对应的是List集合中的Dish对象

            CommentDto commentDto=new CommentDto();//new出来的是新对象所以需要重新拷贝对象

            BeanUtils.copyProperties(item, commentDto);

            Long userId = item.getUserId();
            User byId = userService.getById(userId);
            commentDto.setNickname(byId.getNickname());
            commentDto.setAvatarUrl(byId.getAvatarUrl());

            return commentDto;
        }).collect(Collectors.toList());
        log.info(list.toString());
        return R.success(listDto);
    }

    @DeleteMapping
    public R deleteComment(@RequestParam Long id){
        commentService.removeById(id);
        return R.success(null);
    }
}
