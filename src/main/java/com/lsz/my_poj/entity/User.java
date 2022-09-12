package com.lsz.my_poj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    private static final long serialVersionUID = 1L;
//    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    //用户名
    private String username;

    //密码
    private String password;

    //昵称
    private String nickname;

    //邮箱
    private String email;

    //电话
    private String phone;

    //状态
    private Integer status;

    //性别
    private String sex;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createTime;

    //头像
    private String avatarUrl;

    //角色
    private String role;

}
