package com.lsz.my_poj.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.entity.User;
import com.lsz.my_poj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    /*
     * 用户列表查询，分页查询
     * */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("查询用户列表");
        log.info("page = {},pageSize = {}", page, pageSize);
        //构造分页构造器
        Page pageInfo = new Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();

        //添加排序条件
        queryWrapper.orderByDesc(User::getCreateTime);

        userService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 用户登录
     *
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user) {
        //    ①. 将页面提交的密码password进行md5加密处理, 得到加密后的字符串
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        log.info(user.toString());
        //②. 根据页面提交的用户名username查询数据库中员工数据信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User us = userService.getOne(queryWrapper);

        //③. 如果没有查询到, 则返回登录失败结果
        if (us == null) {
            return R.error("登录失败捏,用户名不存在");
        }

        //④. 密码比对，如果不一致, 则返回登录失败结果
        if (!us.getPassword().equals(password)) {
            return R.error("登录失败捏，密码错辣");
        }

        //⑤. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (us.getStatus() == 0) {
            return R.error("oops,账号已被禁用");
        }

        //⑥. 登录成功，将员工id存入Session, 并返回登录成功结果
        request.getSession().setAttribute("admin", us.getId());
        return R.success(us);
    }

    /**
     * 用户注册
     *
     * @param request
     * @param user
     * @return
     */
    @PostMapping("register")
    public R<String> register(HttpServletRequest request, @RequestBody User user) {
        log.info(user.toString());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User user2 = userService.getOne(queryWrapper);
        if (user2 != null) {
            return R.error("账号已存在");
        }
        queryWrapper.clear();
        queryWrapper.eq(User::getNickname, user.getNickname());
        user2 = userService.getOne(queryWrapper);
        if (user2 != null) {
            return R.error("用户名已存在");
        }
        //   将页面提交的密码password进行md5加密处理, 得到加密后的字符串
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        user.setStatus(1);
        user.setRole("普通用户");
        userService.save(user);
        return R.success("注册成功");
    }

    /**
     * 用户删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deletUser(@RequestParam("ids")  List<Long> ids) {
        log.info("ids:{}", ids);
        for (Long id : ids) {
            userService.removeById(id);
        }
        return R.success("用户删除成功");
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("username", "账号");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "用户名");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("sex", "性别");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("role", "用户类型");
        writer.addHeaderAlias("status", "用户状态");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public R<String> imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(2).toString());
            user.setPassword(row.get(3).toString());
            user.setNickname(row.get(4).toString());
            user.setEmail(row.get(5).toString());
            user.setPhone(row.get(6).toString());
            user.setSex(row.get(7).toString());
            user.setCreateTime(LocalDateTime.now());
            user.setRole(row.get(9).toString());
            user.setStatus(Integer.valueOf(row.get(10).toString()));
            users.add(user);
        }

        userService.saveBatch(users);
        return R.success("导入成功");
    }

    /**
     * 头像上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        System.out.println(file);
        return R.success("头像上传成功");
    }

    /**
     * 更新用户信息
     *
     * @param
     * @return
     */
    @PutMapping
    public R<User> updateWithFlavor(@RequestBody User user) {
        log.info(user.toString());
        userService.updateById(user);
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,user.getId());
        User one = userService.getOne(queryWrapper);
        return R.success(one);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping
    public R<User> getBy(@RequestParam Long id) {
        log.info(id.toString());
        User user = userService.getById(id);
        return R.success(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PostMapping("/update")
    public R<User> update( @RequestBody User user) {
        log.info(user.toString());
        userService.updateById(user);
        User byId = userService.getById(user.getId());
        return R.success(byId);
    }
}
