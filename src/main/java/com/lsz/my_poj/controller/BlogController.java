package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.dto.BlogDto;
import com.lsz.my_poj.dto.CommentDto;
import com.lsz.my_poj.entity.Blog;
import com.lsz.my_poj.entity.Comment;
import com.lsz.my_poj.entity.User;
import com.lsz.my_poj.service.BlogService;
import com.lsz.my_poj.service.CommentService;
import com.lsz.my_poj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/blog")
@CrossOrigin("*")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;
    /**
     * 新增博客
     * @param blog
     * @return
     */
    @PostMapping("/edit")
    public R edit(@RequestBody Blog blog) {
        log.info(blog.toString());
        blogService.save(blog);
        return R.success(null);
    }

    @GetMapping
    public R<List<BlogDto>> blogs(Integer currentPage) {
        List<BlogDto> listDto = null;
        LambdaQueryWrapper<Blog> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Blog::getCreateTime);
        List<Blog> list = blogService.list(queryWrapper);

        listDto = list.stream().map((item) -> {//item对应的是List集合中的Dish对象

            BlogDto blogDto = new BlogDto();//new出来的是新对象所以需要重新拷贝对象

            BeanUtils.copyProperties(item, blogDto);

            Long userId = item.getUserId();
            Long id = item.getId();
            User byId = userService.getById(userId);
            blogDto.setNickname(byId.getNickname());
            blogDto.setAvatarUrl(byId.getAvatarUrl());


            List<CommentDto> commentDtoList = null;
            LambdaQueryWrapper<Comment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Comment::getBlogId,id);
            lambdaQueryWrapper.orderByAsc(Comment::getCreateTime);
            List<Comment> commentslist = commentService.list(lambdaQueryWrapper);

            commentDtoList = commentslist.stream().map((item1) -> {//item对应的是List集合中的Dish对象

                CommentDto commentDto=new CommentDto();//new出来的是新对象所以需要重新拷贝对象

                BeanUtils.copyProperties(item1, commentDto);

                Long userId1 = item1.getUserId();
                User byId1 = userService.getById(userId1);
                commentDto.setNickname(byId1.getNickname());
                commentDto.setAvatarUrl(byId1.getAvatarUrl());

                return commentDto;
            }).collect(Collectors.toList());
            blogDto.setCommentDtoList(commentDtoList);

            return blogDto;
        }).collect(Collectors.toList());


        return R.success(listDto);
    }

    @DeleteMapping
    public R delete(@RequestParam Long id) {
        blogService.removeById(id);
        return R.success(null);
    }
}

