package com.lsz.my_poj.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String blogId;

    //内容
    private String content;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;
}

