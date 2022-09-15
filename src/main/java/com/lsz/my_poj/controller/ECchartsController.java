package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lsz.my_poj.common.Echarts;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.entity.Book;
import com.lsz.my_poj.entity.Sports;
import com.lsz.my_poj.mapper.BookMapper;
import com.lsz.my_poj.mapper.SportsMapper;
import com.lsz.my_poj.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/echarts")
@CrossOrigin("*")
public class ECchartsController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private SportsMapper sportsMapper;

    @GetMapping("/pie")
    public R<List<Echarts>> pie() {
        List<Echarts> list = new ArrayList<Echarts>();
        List<Sports> sportslist = sportsMapper.selectList(null);
        for (Sports sports : sportslist) {
            String names = sports.getName();
            LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Book::getSportsName, names);
            Integer integer = bookMapper.selectCount(queryWrapper);
            list.add(new Echarts(names, integer));
        }
        return R.success(list);
    }

    @GetMapping("/one")
    public R<List<Echarts>> one(@RequestParam String phone) {
        List<Echarts> list = new ArrayList<Echarts>();

        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.groupBy("date_day");
        queryWrapper.eq("beizhu",phone);
        queryWrapper.select("date_day, count(*) as total_count");
        List<Book> books = bookMapper.selectList(queryWrapper);
        for (Book book : books) {
            String dateDay = book.getDateDay();
            Integer totalCount = book.getTotalCount();
            list.add(new Echarts(dateDay, totalCount));
        }
        log.info(books.toString());
        return R.success(list);
    }
}
