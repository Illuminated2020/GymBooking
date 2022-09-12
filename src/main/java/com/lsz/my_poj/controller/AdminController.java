package com.lsz.my_poj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.my_poj.common.R;
import com.lsz.my_poj.entity.Admin;
import com.lsz.my_poj.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /*
    * 管理员后台登录验证
    *

    * */
    @PostMapping("/login")
    public R<Admin> login(HttpServletRequest request, @RequestBody Admin admin) {
        //    ①. 将页面提交的密码password进行md5加密处理, 得到加密后的字符串
        String password = admin.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(password);

        //②. 根据页面提交的用户名username查询数据库中员工数据信息
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,admin.getUsername());
        Admin adm = adminService.getOne(queryWrapper);

        //③. 如果没有查询到, 则返回登录失败结果
        if (adm==null) {
            return R.error("登录失败捏,用户名不存在");
        }

        //④. 密码比对，如果不一致, 则返回登录失败结果
        if (!adm.getPassword().equals(password)) {
            return R.error("登录失败捏，密码错辣");
        }

        //⑤. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (adm.getStatus() == 0) {
            return R.error("oops,账号已被禁用");
        }

        //⑥. 登录成功，将员工id存入Session, 并返回登录成功结果
        request.getSession().setAttribute("admin", adm.getId());
        return R.success(adm);
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return R.success("退出成功辣");
    }
}
