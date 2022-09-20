package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.entity.Book;
import com.lsz.my_poj.entity.Sports;
import com.lsz.my_poj.mapper.BookMapper;
import com.lsz.my_poj.service.BookService;
import com.lsz.my_poj.service.SportsService;
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

    @Autowired
    private SportsService sportsService;
    @PostMapping
    public R<String> order(@RequestBody Book order) {
        log.info(order.toString());
        String sportsName = order.getSportsName();
        LambdaQueryWrapper<Sports> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Sports::getName,sportsName);
        Sports one = sportsService.getOne(queryWrapper);
        one.setCount(one.getCount()+1);
        sportsService.updateById(one);
        order.setStatus(0);
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

    @GetMapping("/mybook")
    public R<List<Book>> Mybook(@RequestParam Long id) {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getUserId,id);
        queryWrapper.orderByDesc(Book::getDateDay);
        List<Book> list = bookService.list(queryWrapper);
        return R.success(list);
    }

    @GetMapping("/cancle")
    public R cancle(@RequestParam Long id) {
        log.info(id.toString());
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getId,id);
        Book book = bookService.getOne(queryWrapper);
        book.setStatus(4);
        bookService.updateById(book);
        return R.success(null);
    }

    @DeleteMapping
    public R<String> deletBooking(@RequestParam("ids")  List<Long> ids) {
        log.info("ids:{}", ids);
        for (Long id : ids) {
            bookService.removeById(id);
        }
        return R.success("预约删除成功");
    }

    @PostMapping("/next")
    public R<String> next(@RequestBody  Book book1) {
        Long id = book1.getId();
        log.info(id.toString());
        LambdaQueryWrapper<Book> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getId,id);
        Book book = bookService.getOne(queryWrapper);
        book.setStatus(book.getStatus()+1);
        bookService.updateById(book);
        return R.success("状态更新成功");
    }
}
