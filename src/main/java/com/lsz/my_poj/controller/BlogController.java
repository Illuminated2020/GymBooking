package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.entity.Blog;
import com.lsz.my_poj.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
@CrossOrigin("*")
public class BlogController {

    @Autowired
    private BlogService blogService;

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
    public R<List<Blog>> blogs(Integer currentPage) {
        List<Blog> list = blogService.list();
        return R.success(list);
    }

}
