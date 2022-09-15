package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.entity.Sports;
import com.lsz.my_poj.entity.User;
import com.lsz.my_poj.service.SportsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/sports")
@Slf4j
@RestController
@CrossOrigin("*")
public class SportsController {

    @Autowired
    private SportsService sportsService;

    /*
     * 用户列表查询，分页查询
     * */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("查询场馆列表");
        log.info("page = {},pageSize = {}", page, pageSize);
        //构造分页构造器
        Page pageInfo = new Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Sports> queryWrapper = new LambdaQueryWrapper();

        //添加排序条件
        queryWrapper.orderByDesc(Sports::getCount);

        sportsService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     *新增场馆
     */
    @PostMapping("add")
    public R<String> register(HttpServletRequest request, @RequestBody Sports sports) {
        log.info(sports.toString());
        sportsService.save(sports);
        return R.success("场馆添加成功");
    }

    /**
     *场馆列表
     */
    @GetMapping("/list")
    public R<List<Sports>> list() {
        List<Sports> list = sportsService.list();
        log.info(list.toString());
        return R.success(list);
    }

    /**
     *预约场馆时展示场馆信息
     */
    @GetMapping("/one")
    public R<Sports> one(@RequestParam String id) {
        log.info(id);
        Sports sports = sportsService.getById(id);
        log.info(sports.toString());
        return R.success(sports);
    }
}
