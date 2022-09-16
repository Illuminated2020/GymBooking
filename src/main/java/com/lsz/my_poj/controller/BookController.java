package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.entity.Book;
import com.lsz.my_poj.mapper.BookMapper;
import com.lsz.my_poj.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
@CrossOrigin("*")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @PostMapping
    public R<String> order(@RequestBody Book order) {
        log.info(order.toString());
        bookService.save(order);
        return R.success("成功");
    }

    /**
     * 预约管理分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("查询用户列表");
        log.info("page = {},pageSize = {}", page, pageSize);
        //构造分页构造器
        Page pageInfo = new Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper();

        //添加排序条件
        queryWrapper.orderByDesc(Book::getDateDay);

        bookService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @GetMapping("/all")
    public R<List<Book>> all() {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("stu_name");
        queryWrapper.select("stu_name, count(*) as total_count");
        List<Book> books = bookMapper.selectList(queryWrapper);
        books.sort(Comparator.comparing(Book::getTotalCount).reversed());
        //只显示前十名数据
        int size = books.size();
        List<Book> res=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if(i==size) break;
            res.add(books.get(i));
        }
        return R.success(res);
    }
}
