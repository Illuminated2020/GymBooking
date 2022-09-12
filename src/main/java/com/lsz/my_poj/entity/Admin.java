package com.lsz.my_poj.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("admin")
public class Admin {
    private static final long serialVersionUID = 1L;
//    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String nickname;

    private String username;

    private String password;

    private String phone;

    private String sex;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;

}
